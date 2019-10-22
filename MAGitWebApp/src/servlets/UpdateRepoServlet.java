package servlets;

import com.google.gson.Gson;
import magit.Engine;
import utils.RepositoryUpdates;
import utils.ServletsUtils;
import utils.SessionUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/pages/update-repo")
public class UpdateRepoServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = SessionUtils.getUsername(request);
        Engine engine = ServletsUtils.getUsersManager(getServletContext()).getEngine(username);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        RepositoryUpdates repositoryUpdates = new RepositoryUpdates(engine);

        if(repositoryUpdates.getRepository() == null) {
            out.print("User has no repositories.");
        } else {
            out.print(gson.toJson(repositoryUpdates));
        }

        out.flush();
    }
}
