
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import org.apache.catalina.filters.CorsFilter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HE170417
 */
public class PersonServlet extends HttpServlet {

    private int PERSON_ID = 1;
    private HashMap<Integer, Person> listPerson = new HashMap<>();
    private Person persontest1 = new Person(1, "Phan Dang", "Truong", 20, true);
    private Person persontest2 = new Person(2, "Phan Phuong", "Thao", 30, false);
    
    @Override
    //chi form moi lam post dc thoi
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(listPerson.isEmpty()){
            listPerson.put(persontest1.getId(), persontest1);
            listPerson.put(persontest2.getId(), persontest2);
        }
        int id;
        if (req.getParameter("id") == null) {
            id = 0;
        } else {
            id = Integer.parseInt(req.getParameter("id"));
        }

        if (action == null || action.compareTo("") == 0) {
            action = "list";
        }
        switch (action) {
            case "list":
                PrintWriter pw = this.displayHeader(req, resp);
                this.displayListPerson(pw);
                this.displayFooter(pw);
                break;
            case "view":
                this.viewPerson(req, resp);
                break;
            case "remove":
                this.removePerson(req, resp);
                break;
        }
    }

    private void removePerson(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id;
        PrintWriter pw = resp.getWriter();
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        Person p = listPerson.get(id);
        if (p == null) {
            resp.sendRedirect("submitPersonList");
        } else {
            listPerson.remove(p);
            resp.sendRedirect("submitPersonList");
        }

    }

    private PrintWriter displayHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        pw.println("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <title>Person List</title>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "</head>\n"
                + "<body>\n");
        return pw;
    }

    private void displayFooter(PrintWriter pw) {
        pw.println("</body>\n"
                + "</html>");
    }

    private void displayListPerson(PrintWriter pw) {
        if (listPerson.size() == 0) {
            pw.println("Empty list");
        } else {
            pw.println("List of " + listPerson.size() + "person <br/>");
            pw.println("<table border=\"1px solid black\">\n");
            for (int i : listPerson.keySet()) {
                Person p = listPerson.get(i);
                pw.println("<tr>");
                pw.println("<td>");
                pw.println("<a href=\"submitPersonList?id=" + i + "&action=view" + "\">Person " + p.getId() + "</a>");
                pw.println("</td>");
                pw.println("<td>");
                pw.println(p);
                pw.println("</td>");
                pw.println("<td>");
                pw.println("<a href=\"submitPersonList?id=" + i + "&action=remove" + "\">Delete" + p.getId() + "</a>");
                pw.println("</td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
        }

    }

    private void viewPerson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id;
        PrintWriter pw = resp.getWriter();
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        Person p = listPerson.get(id);
        if (p == null) {
            resp.sendRedirect("submitPersonList");
        } else {
            pw.println("<h1>"+p.toString()+"</h1>");
        }
    }

}
