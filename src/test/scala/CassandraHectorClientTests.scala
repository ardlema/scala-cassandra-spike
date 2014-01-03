package ardlema.scalacassandraspike

import org.scalatest.FunSpec
import me.prettyprint.hector.api.exceptions.HectorException

class CassandraHectorClientTests extends FunSpec {

   it("should add data to cassandra db") {
     val userId = "12346"
     val userName = "paco"
     val userSurname = "gonzalez"

     val theKeySpace = CassandraHectorClient.getKeySpace("keySpaceTest1")
     val template = CassandraHectorClient.getTemplate(theKeySpace)
     val updater = template.createUpdater(userId)
     updater.setString("name", userName)
     updater.setString("surname", userSurname)
     template.update(updater)

     val res = template.queryColumns(userId)
     assert(res.getString("name")==userName)
     assert(res.getString("surname")==userSurname)

     template.deleteRow(userId)

     try {
       template.update(updater)
     } catch {
       case hE: HectorException => fail(hE)
     }
   }

}
