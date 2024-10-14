import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordProtectedApp {

    // 存储密码
    private static final String CORRECT_PASSWORD = "yourPassword"; 

    public static void main(String[] args) 
        // 使用SwingUtilities.invokeLater确保GUI的创建和更新在事件调度线程上执行（借鉴）
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    // 创建并显示GUI的方法
    private static void createAndShowGUI() {
        // 创建应用程序的主窗口
        JFrame frame = new JFrame("密码验证");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认的关闭操作
        frame.setSize(300, 150); 
        frame.setLocationRelativeTo(null); // 居中显示

        // 创建放置组件
        JPanel panel = new JPanel();
        frame.add(panel); 

        // 调用方法来放置组件
        placeComponents(panel);

        // 设置窗口可见
        frame.setVisible(true);
    }

    // 放置组件
    private static void placeComponents(JPanel panel) {
        // 设置面板布局为null，手动设置组件位置
        panel.setLayout(null);

        // 创建一个标签提示用户输入密码
        JLabel userLabel = new JLabel("请输入密码:");
        userLabel.setBounds(10, 20, 80, 25); 

        panel.add(userLabel); // 将标签添加到面板上

        // 创建一个密码字段供用户输入密码
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 20, 165, 25); 
        panel.add(passwordField); // 将密码字段添加到面板上

        // 创建一个登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setBounds(10, 60, 80, 25); 
        panel.add(loginButton); // 将按钮添加到面板上

        // 为登录按钮添加一个动作监听器
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的密码
                char[] input = passwordField.getPassword();
                String inputPassword = new String(input); // 将字符数组转换为字符串
                
                if (isPasswordCorrect(inputPassword)) {
                    // 如果密码正确
                    JOptionPane.showMessageDialog(panel, "密码正确，正在进入应用程序...");
                    
                } else {
                    // 如果密码错误
                    JOptionPane.showMessageDialog(panel, "密码错误，请重试。");
                }
            }
        });
    }

    // 检查输入的密码是否正确
    private static boolean isPasswordCorrect(String inputPassword) {
        // 将输入的密码与正确的密码进行比较
        return inputPassword.equals(CORRECT_PASSWORD);
    }
