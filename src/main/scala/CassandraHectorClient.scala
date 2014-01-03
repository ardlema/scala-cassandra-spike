package ardlema.scalacassandraspike

import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.hector.api.ddl.ComparatorType
import me.prettyprint.cassandra.service.ThriftKsDef
import java.util._
import me.prettyprint.hector.api.{Keyspace, Cluster}
import me.prettyprint.cassandra.service.template.{ThriftColumnFamilyTemplate, ColumnFamilyTemplate}
import me.prettyprint.cassandra.serializers.StringSerializer

object CassandraHectorClient {
  val columnFamilyName = "ColumnFamilyName"

  def getKeySpace(keySpaceName: String) =  {
    val cluster = HFactory.getOrCreateCluster("test-cluster","localhost:9160")
    //Once the schema is created, the previous call will throw an exception saying that the Keyspace we are trying to create already exists. In order to solve that, you can wrap the previous code in a method called for example: "createSchema();" and add the following lines:
    val keyspaceDef = cluster.describeKeyspace(keySpaceName)

    // If keyspace does not exist, the CFs don't exist either. => create them.
    if (keyspaceDef == null) {
      createSchema(keySpaceName, cluster)
    }

    //Finally, we’ll create a Keyspace object which is a long life component and represents the Cassandra keyspace under which we will perform operations.
    HFactory.createKeyspace(keySpaceName, cluster)
  }

  def getTemplate(keySpace: Keyspace) = {
    new ThriftColumnFamilyTemplate(keySpace, columnFamilyName, StringSerializer.get(), StringSerializer.get())
  }

  private def createSchema(keySpaceName: String, cluster: Cluster) {
    val replicationFactor = 1
    // Let’s set up the schema.
    val cfDef = HFactory.createColumnFamilyDefinition(keySpaceName, columnFamilyName, ComparatorType.BYTESTYPE)
    val newKeyspace = HFactory.createKeyspaceDefinition(keySpaceName, ThriftKsDef.DEF_STRATEGY_CLASS, replicationFactor, Arrays.asList(cfDef))

    // Add the schema to the cluster. "true" as the second param means that Hector will block until all nodes see the change.
    cluster.addKeyspace(newKeyspace, true)
  }
}
