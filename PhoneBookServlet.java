
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
/*
http://localhost:8080/servlets/
*/
public class PhoneBookServlet extends HttpServlet  {
    private static final Logger logger = Logger.getLogger(HelloServlet.class.getName());
    private static final PhoneBook phoneBook = PhoneBookSingleton.getInstance();

    @Override
    public void init(ServletConfig config) {
        phoneBook.readFile();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>\n");
            String name = request.getParameter("name");
            String phone;
            char first = request.getParameter("phone").charAt(0);

            if(first == '+') {
                phone = request.getParameter("phone");
            }
            else {
                phone = "+" + request.getParameter("phone").replaceAll("\\s", "");
            }




            if (isValidPhoneNumber(phone)) {
                if (!phoneBook.containName(name)) {
                    if (!name.equals("")) {
                        phoneBook.addNamePhone(name, phone);
                        out.println("<h2>The user was successfully added</h2>\n");
                        logger.info("The user " + name + ", " + phone + " was successfully added");
                    }
                } else {
                    if (!phoneBook.containPhone(name, phone)) {
                        if (phone.equals("")) {
                            out.println("<h2>This user was already added</h2>\n");
                        } else {
                            phoneBook.addNamePhone(name, phone);
                            out.println("<h2>The user was successfully added</h2>\n");
                            logger.info("The user " + name + ", " + phone + " was successfully added");
                        }
                    } else {
                        out.println("<h2>This phone was already added to the list</h2>\n");
                        logger.info("This phone was already added to the list");
                    }
                }
            } else {
                out.println("<h2>The phone must contain only numbers and start with +7</h2>\n");
                logger.info("The phone must contain only numbers and start with +7");
                logger.info("name: " + name + ", phone: " + phone);
            }

            out.println("</body>\n</html>");
            phoneBook.writeFile();
        } catch (IOException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            showAddRecordPage(response);
        } else {
            showRecordList(response);
        }
    }

    private void showAddRecordPage(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>\n");
            out.println("<form method=\"POST\" action=\"records?action=add\">");
            out.println("Name: <input type=\"text\" name=\"name\"><br><br>");
            out.println("Phone: <input type=\"text\" name=\"phone\"><br><br>");
            out.println("<input type=\"submit\" value=\"Add\">");
            out.println("</form>");
            out.println("</body>\n</html>");
        } catch (IOException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    private void showRecordList(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>\n");
            out.println("<h2>List of entries</h2");

            HashMap<String, ArrayList<String>> phoneBookData = phoneBook.getPhoneBook();

            if (phoneBookData.isEmpty()) {
                out.println("<p>No entries.</p>");
            } else {
                out.println("<ul>");
                for (Map.Entry<String, ArrayList<String>> entry : phoneBookData.entrySet()) {
                    String name = entry.getKey();
                    ArrayList<String> phones = entry.getValue();
                    out.println("<li>" + name + ": " + String.join(", ", phones) + "</li>");
                }
                out.println("</ul>");
            }
        } catch (IOException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }
    private boolean isValidPhoneNumber(String str) {
        if (str.startsWith("+7") && str.length() == 12) {
            String digits = str.substring(2);
            return isDigitsOnly(digits);
        }
        return false;
    }

    private boolean isDigitsOnly(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
