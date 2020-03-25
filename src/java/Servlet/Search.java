/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Class.Item;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "executaEvento", urlPatterns ={"/executaEvento"})
public class Search extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter())
        {
            StringBuilder content = new StringBuilder();

            try
            {
                URL url = new URL("https://www.albion-online-data.com/api/v2/stats/history/" + request.getParameter("item_id").toString() + "?time-scale=1");
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line = bufferedReader.readLine();
                JSONArray array = new JSONArray(line);
                JSONArray data;
                JSONObject json_ciry, json_item;
                String location, count, price, time;
                ArrayList<Item> itens;
                Item aux;
                int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, avg = 0;
                
                for(Object obj_city: array)
                {
                    itens = new ArrayList<Item>();
                    json_ciry = new JSONObject(obj_city.toString());
                    location = json_ciry.get("location").toString();
                    
                    data = new JSONArray(json_ciry.get("data").toString());
                    for(Object obj_item: data)
                    {
                        json_item = new JSONObject(obj_item.toString());
                        count = json_item.get("item_count").toString();
                        price = json_item.get("avg_price").toString();
                        
                        aux = new Item(location, count, price);
                        
                        min = Math.min(min, aux.getPrice());
                        max = Math.max(max, aux.getPrice());
                        avg += aux.getPrice();
                    }
                    avg /= data.length();
                    line =  "<table>\n" +
                            "  <tr>\n" +
                            "    <th>" + location + "</th>\n" +
                            "  </tr>\n" +
                            "  <tr>\n" +
                            "    <td>Min</td>\n" +
                            "    <td>Avg</td>\n" +
                            "    <td>Max</td>\n" +
                            "  </tr>\n" +
                            "  <tr>\n" +
                            "    <td>" + min + "</td>\n" +
                            "    <td>" + avg + "</td>\n" +
                            "    <td>" + max + "</td>\n" +
                            "  </tr>\n" +
                            "</table>\n" +
                            "<br>";
                    
                    content.append(line);
                }
                
                
                bufferedReader.close();
                out.println(content.toString());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                response.reset();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
