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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
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
                //URL url = new URL("https://www.albion-online-data.com/api/v1/stats/Charts/" + request.getParameter("item_id").toString());
                URL url = new URL("https://www.albion-online-data.com/api/v2/stats/history/" + request.getParameter("item_id").toString() + "?qualities=1&time-scale=1");
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line = bufferedReader.readLine();
                String head, body = "", location;
                JSONArray array = new JSONArray(line), data;
                JSONObject json_ciry, json_item;
                Item aux;
                ArrayList<Item> itens;
                
                head =  "<table>\n" +
                            "  <tr>\n" +
                            "    <th>Location</th>\n" +
                            "    <th>Avg</th>\n" +
                            "  </tr>";
                
                for(Object obj_city: array)
                {
                    int avg = 0;
                    
                    itens = new ArrayList<Item>();
                    json_ciry = new JSONObject(obj_city.toString());
                    location = json_ciry.get("location").toString();
                    
                    data = new JSONArray(json_ciry.get("data").toString());
                    
                    for(int i = 0; i < data.length(); i++)
                    {
                        json_item = new JSONObject(data.get(i).toString());
                        
                        aux = new Item(location, "" + json_item.getInt("item_count"), "0", "" + json_item.getInt("avg_price"), "0", json_item.getString("timestamp"));
                        itens.add(aux);     
                        avg += json_item.getInt("avg_price");
                    }
                    avg /= data.length();
                    
                    head += 
                            "  <tr>\n" +
                            "    <td>" + location + "</td>\n" +
                            "    <td>" + avg + "</td>\n" +
                            "  </tr>";
                    
                    itens.sort(new Comparator<Item> ()
                    {
                        @Override
                        public int compare(Item o1, Item o2)
                        {
                            return o1.getTime().compareTo(o2.getTime());
                        }
                    });
                    
                    line =  "<table>\n" +
                            "  <tr>\n" +
                            "    <th>Location</th>\n" +
                            "    <th>Avg</th>\n" +
                            "  </tr>\n" +
                            "  <tr>\n" +
                            "    <td>" + location + "</td>\n" +
                            "    <td>" + avg + "</td>\n" +
                            "  </tr>\n" +
                            "  \n" +
                            "</table><br>";
                    
                    line += "<table>\n"+
                            "  <tr>\n" +
                            "    <th>Count</th>\n" +
                            "    <th>Avg</th>\n" +
                            "    <th>Time</th>\n" +
                            "  </tr>\n";
                    
                    int n = itens.size() > 10 ? 10 : itens.size();
                    for(int i = 0; i < n; i++)
                    {   
                        line +=  
                            "  <tr>\n" +
                            "    <td>" + itens.get(i).getCount() + "</td>\n" +
                            "    <td>" + itens.get(i).getAvg() + "</td>\n" +
                            "    <td>" + itens.get(i).getTime()+ "</td>\n" +
                            "  </tr>";
                            
                    }
                    line += "</table><br>";
                    
                    body += line;
                }
                head += "</table><br>";
                
                content.append(head + body);
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
