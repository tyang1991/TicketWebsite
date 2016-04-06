package servlet;

import beans.SortingBean;
import util.PostConnection;
import util.QueryFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by pianobean on 4/12/15.
 */
@WebServlet(name = "Checkout", urlPatterns = "/checkout")
public class Checkout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int index = Integer.parseInt(request.getParameter("index"));
        HttpSession session = request.getSession(false);
        List showInfo = (List) session.getAttribute("showInfo");
        Map.Entry entry = (Map.Entry) showInfo.get(index - 1);
        session.setAttribute("checkout",entry);
        PostConnection.DBOperate(QueryFactory.lock());
        request.getRequestDispatcher("/checkout.jsp").forward(request,response);
    }
}
