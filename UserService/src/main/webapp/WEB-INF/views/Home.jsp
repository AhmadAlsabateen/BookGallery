<%@page import="java.util.ArrayList" %>
<%@page import="entities.*" %>
<%@page import="services.beans.*" %>


<html>
  <head>
   <script>
   function downloadBook(element) {

        let bookId = element.getAttribute('data-id');
        let url =  window.location.protocol + "//" + window.location.host+'/S3Management/download?bookId='+bookId;
        let xmlHttp = new XMLHttpRequest();
        xmlHttp.onreadystatechange = function() {
                   if (this.readyState === this.DONE) {
                           let downloadURL = xmlHttp.responseText;
                           var link = document.createElement("a");
                           link.setAttribute('download', '');
                           link.href = downloadURL;
                           document.body.appendChild(link);
                           link.click();
                           link.remove();
                   }
               }
        xmlHttp.open('GET', url);
        xmlHttp.send();

   }
   </script>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>BooksGallery</title>
  </head>
  <body>

      <FORM METHOD="GET" ACTION="/User/userHome" align="center">
<table border="1" cellpadding="5" align="center">
            <tr>
                <th>Name</th>
                <th>Author</th>
                <th>Category</th>
            </tr>
                <tr>
                    <td><input type="text"  name="Name"</td>
                    <td><input type="text"  name="Author" </td>
                    <td><input type="text"  name="Category"</td>
                </tr>
        </table>
      <P><INPUT TYPE="SUBMIT" VALUE="Submit" NAME="B1"></P>
      </FORM>
      <% if(request.getAttribute("books")!= null){%>
      <table border ="1" width="500" align="center">
           <tr bgcolor="00FF7F">
           <th><b>Author</b></th>
           <th><b>Category Name</b></th>
           <th><b>Name</b></th>
           <th><b>download</b></th>
         </tr>
       <%ArrayList<BookBean> books = (ArrayList<BookBean>)request.getAttribute("books");
        for(BookBean book:books){%>
        <%-- Arranging data in tabular form
        --%>
            <tr>
                <td><%=book.author.name%></td>
                <td><%=book.category.name%></td>
                <td><%=book.name%></td>
                <td><a data-id="<%=book.id%>" onClick="downloadBook(this)">download</a></td>
            </tr>
            <%}%>
        </table>
<% } %>
    </body>
</html>