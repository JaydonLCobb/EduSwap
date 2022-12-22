
import java.util.List;
import java.util.ArrayList;

import EduSwapApplication.AccountInfo.AccountInfoController;
import EduSwapApplication.AccountInfo.AccountInfoRepository;
import EduSwapApplication.Users.User;
import EduSwapApplication.Users.UserController;
import EduSwapApplication.Users.UserRepository;

// import junit/spring tests
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserController.class })
@AutoConfigureMockMvc
public class Testing {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AccountInfoRepository accountInfoRepository;
    @Autowired
    private MockMvc controller;

    @Test
    public void createUser() throws Exception {
        String body = "{\"id\": 1,\"username\":\"tjt\",\"password\":\"123\",\"type\":1,\"email\":\"tjtln@iastate.edu\",\"isVerified\":true,\"creationDate\":\"9/30/22\",\"lastLogin\":\"9/30/22\", \"isEnabled\":true}";
        // controller
        //         .perform(post("/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
        //                 .content(body))
        //         .andExpect(status().isOk());
    }

}
