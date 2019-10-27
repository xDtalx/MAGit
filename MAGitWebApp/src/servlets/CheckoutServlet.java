package servlets;

import MagitExceptions.OpenChangesInWcException;
import com.google.gson.Gson;
import magit.Engine;
import utils.ServletsUtils;
import utils.SessionUtils;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/pages/checkout")
public class CheckoutServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = SessionUtils.getUsername(request);
            String userToSendRepo = SessionUtils.getUserRepo(request);

            if(username.equals(userToSendRepo)) {
                String branchName = request.getParameter("branchname");
                boolean isCheckWc = request.getParameter("checkwc").equals("false");
                Engine engine = ServletsUtils.getUsersManager(getServletContext()).getEngine(userToSendRepo == null ? username : userToSendRepo);
                String repoName = engine.getActiveRepositoryName();
                engine.checkout(repoName, branchName, isCheckWc);
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.print("Not user's repository");
            }
        } catch (OpenChangesInWcException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("There are open changes in working directory.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
