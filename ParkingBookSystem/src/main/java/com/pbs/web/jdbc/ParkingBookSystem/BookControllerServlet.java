package com.pbs.web.jdbc.ParkingBookSystem;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
// import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import javax.sql.DataSource;

/**
 *
 * @author lukas
 */
@WebServlet(name = "BookControllerServlet", urlPatterns = {"/BookControllerServlet"})
public class BookControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private BookDbUtil bookDbUtil;

    // init method will be called by the app server when this servlet is loaded or initialized
    @Override // we inherit it from GenericServlet
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.

        // create our book db util ... and pass in the connection pool / datasource
        try {
            bookDbUtil = new BookDbUtil(); // bookDbUtil is a data member that we've defined
        } // dataSource is resource injection item our connection pool and we're passing it right here
        catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // read the "command" parameter
            String theCommand = request.getParameter("command");

            // if the command is missing, then default to listing booked places
            if (theCommand == null) {
                theCommand = "LIST";
            }

            // route to the appropriate method
            switch (theCommand) {
                case "LIST":
                    listBookedPlaces(request, response);
                    break;

                case "ADD":
                    addBook(request, response);
                    break;

                default:
                    listBookedPlaces(request, response);
            }

            // list the booked places ... in MVC fashion
        } catch (Exception ex) {
            Logger.getLogger(BookControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listBookedPlaces(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // get list of booked places from db util
        List<Book> books = bookDbUtil.getBooks();

        // add booked places and flag to the request
        request.setAttribute("BOOKED_LIST", books); // set Attribute  (-name "BOOKED_LIST", -value books);

        // send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-book.jsp");
        dispatcher.forward(request, response);
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) throws ParseException, Exception {
        // response for user
        String info;
        
        // read date info from html form data
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        int number = Integer.parseInt(request.getParameter("number"));
        String startStr = request.getParameter("start");
        String endStr = request.getParameter("end");
        Date start = formatter.parse(startStr);
        Date end = formatter.parse(endStr);
        String userName = request.getParameter("userName");
        int phone = Integer.parseInt(request.getParameter("phone"));

        // request.setAttribute("SELECTED_NO", number);

        // create new book object
        Book theBook = new Book(number, start, end, userName, phone);

        boolean exists = bookDbUtil.doesBookExist(number, start, end);

        if (exists) {
            // does something when book already exist
            info = "Unfortunatelly selected place is already booked in chosen time. Please check places avability:";
            request.setAttribute("USER_RESPONSE", info);
            listBookedPlaces(request, response);
        } else {
            // add book when book does NOT exist
            info = "Selected place has been booked!";
            request.setAttribute("USER_RESPONSE", info);
            bookDbUtil.addBook(theBook);
            listBookedPlaces(request, response);
        }
    }
}
