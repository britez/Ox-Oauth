import java.util.Date;

import com.ox.oauth.User

class BootStrap {

    def init = { servletContext ->
		
		User user = new User(mail:"maximiliano.britez@globallogic.com",
							 accessToken: "accessToken",
							 refreshToken: "refreshToken",
							 displayName: "Maximiliano Britez",
							 image: "https://lh6.googleusercontent.com/-5N5tiTmb3oY/UwdcVu-qzmI/AAAAAAAAAa8/ibgaWGxLLDM/s639-no/487577_10202309705009852_729141458_n.jpg",
							 createdDate: new Date(),
							 expire: 6000L)
		user.save(flush:true)
    }
	
    def destroy = {}
}
