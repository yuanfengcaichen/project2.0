package club.codeqi.project.pojo;



/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/29 0029 10:38
 */
class UserTest {

    public static void creatPassword(){
        String oldpass = "admin";
        String encode = User.bCryptPasswordEncoder.encode(oldpass);
        System.out.println(encode);
    }

    public static void main(String[] args) {
        creatPassword();
    }
}