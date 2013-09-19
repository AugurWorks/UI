import com.augurworks.web.Role
import com.augurworks.web.User
import com.augurworks.web.UserRole

class BootStrap {
	
	def init = { servletContext ->
		def adminRole = new Role(authority: 'Admin').save(flush: true)
		def userRole = new Role(authority: 'User').save(flush: true)
		def admin = new User(username:'Admin', password:'admin', enabled:true).save()
		def user = new User(username:'User', password:'user', enabled:true).save()
		def adminUserRole = new UserRole(user: admin, role: adminRole).save()
		def userUserRole = new UserRole(user: user, role: userRole).save()
		
	}
	def destroy = {
	}
}
