package org.csu.mypetstore.api.controller.front;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "CorsFilter")
//@Configuration
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) res;
        //使用xhrFields后Access-Control-Allow-Origin
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8888");
        //使用xhrFields前的Access-Controller-Allow-Orgin
        //response.setHeader("Access-Controller-Allow-Orgin","*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Methods","POST,GET,PATCH,DELETE,PUT");
        response.setHeader("Access-Control-Max-Age","3600");
        response.setHeader("Access-Control-Allow-Headers","Origin,X-Requested-With,Content-Type,Accept");
        chain.doFilter(req,res);
    }

}
