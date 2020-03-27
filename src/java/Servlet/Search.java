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
import java.util.Collection;
import java.util.Comparator;
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
                URL url = new URL("https://www.albion-online-data.com/api/v1/stats/Charts/" + request.getParameter("item_id").toString());
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line = bufferedReader.readLine();
                String head, body = "", location;
                JSONArray array = new JSONArray(line);
                JSONArray a_min, a_avg, a_max, a_count, a_time;
                JSONObject json_ciry, json_item, data;
                Item aux;
                ArrayList<Item> itens;
                
                head =  "<table>\n" +
                            "  <tr>\n" +
                            "    <th>Location</th>\n" +
                            "    <th>Min</th>\n" +
                            "    <th>Avg</th>\n" +
                            "    <th>Max</th>\n" +
                            "  </tr>";
                
                for(Object obj_city: array)
                {
                    int min = Integer.MAX_VALUE, avg = 0, max = Integer.MIN_VALUE;
                    
                    itens = new ArrayList<Item>();
                    json_ciry = new JSONObject(obj_city.toString());
                    location = json_ciry.get("location").toString();
                    
                    data = new JSONObject(json_ciry.get("data").toString());
                    a_time = new JSONArray(data.get("timestamps").toString());
                    a_min = new JSONArray(data.get("prices_min").toString());
                    a_avg = new JSONArray(data.get("prices_avg").toString());
                    a_max = new JSONArray(data.get("prices_max").toString());
                    a_count = new JSONArray(data.get("item_count").toString());
                    
                    for(int i = 0; i < a_time.length(); i++)
                    {
                        aux = new Item(location, a_count.get(i).toString(), a_min.get(i).toString(), a_avg.get(i).toString(), a_max.get(i).toString(), a_time.get(i).toString());
                        itens.add(aux);
                        
                        min = Integer.min(min, aux.getMin());
                        avg += aux.getAvg();
                        max = Integer.max(max, aux.getMax());                        
                    }
                    avg /= a_time.length();
                    
                    head += 
                            "  <tr>\n" +
                            "    <td>" + location + "</td>\n" +
                            "    <td>" + min + "</td>\n" +
                            "    <td>" + avg + "</td>\n" +
                            "    <td>" + max + "</td>\n" +
                            "  </tr>";
                    
                    itens.sort(new Comparator<Item> ()
                    {
                        @Override
                        public int compare(Item o1, Item o2)
                        {
                            return o1.getTime().compareTo(o2.getTime()) * -1;
                        }
                    });
                    
                    line =  "<table>\n" +
                            "  <tr>\n" +
                            "    <th>Location</th>\n" +
                            "    <th>Min</th>\n" +
                            "    <th>Avg</th>\n" +
                            "    <th>Max</th>\n" +
                            "  </tr>\n" +
                            "  <tr>\n" +
                            "    <td>" + location + "</td>\n" +
                            "    <td>" + min + "</td>\n" +
                            "    <td>" + avg + "</td>\n" +
                            "    <td>" + max + "</td>\n" +
                            "  </tr>\n" +
                            "  \n" +
                            "</table><br>";
                    
                    line += "<table>\n"+
                            "  <tr>\n" +
                            "    <th>Count</th>\n" +
                            "    <th>Min</th>\n" +
                            "    <th>Avg</th>\n" +
                            "    <th>Max</th>\n" +
                            "  </tr>\n";
                    
                    int n = itens.size() > 10 ? 10 : itens.size();
                    for(int i = 0; i < n; i++)
                    {
                        line +=  
                            "  <tr>\n" +
                            "    <td>" + itens.get(i).getCount() + "</td>\n" +
                            "    <td>" + itens.get(i).getMin() + "</td>\n" +
                            "    <td>" + itens.get(i).getAvg() + "</td>\n" +
                            "    <td>" + itens.get(i).getMax() + "</td>\n" +
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
