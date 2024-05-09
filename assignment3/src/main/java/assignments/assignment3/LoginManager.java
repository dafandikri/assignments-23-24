package assignments.assignment3;

import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;

    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    // Get the system based on the role
    public UserSystemCLI getSystem(String role){
        if(role.equals("Customer")){
            return customerSystem;
        }else{
            return adminSystem;
        }

        return adminSystem;
    }
}