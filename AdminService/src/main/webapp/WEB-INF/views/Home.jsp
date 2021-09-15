<%@page import="java.util.ArrayList" %>
<%@page import="entities.*" %>
<%@page import="services.beans.*" %>


<html>
  <head>


      <style>
      body {font-family: Arial, Helvetica, sans-serif;}

      /* The Modal (background) */
      .modal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        padding-top: 100px; /* Location of the box */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
      }

      /* Modal Content */
      .modal-content {
        background-color: #fefefe;
        margin: auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
      }

      /* The Close Button */
      .close {
        color: #aaaaaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
      }

      .close:hover,
      .close:focus {
        color: #000;
        text-decoration: none;
        cursor: pointer;
      }
      </style>


   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>BooksGallery</title>
  </head>
  <body>
  <a href="/Auth/logout">logout</a>
       <div id="Modal" class="modal">
         <!-- Modal content -->
         <div class="modal-content">
           <span class="close">&times;</span>
		   <form action = "/Admin/adminHome" method = "POST"  align="center" enctype="multipart/form-data">
           <table border ="1" width="500" align="center">
              <tr bgcolor="00FF7F">
              <th><b>Author</b></th>
              <th><b>Category Name</b></th>
              <th><b>Name</b></th>
              <th><b>File</b></th>
              </tr>
              <tr>
              <td><input type="text" name="Author"</td>
              <td><input type="text" name="Category"</td>
              <td><input type="text" name="Name"</td>
              <td><input type="file" name="File"</td>
              </tr>
           </table>
		   <P><INPUT TYPE="SUBMIT" VALUE="Add Book" NAME="B2"></P>
		   </form>
         </div>
       </div>

       <div id="Edit" class="modal">
                <!-- Modal content -->
                <div class="modal-content">
                  <span id="edit-span" class="close">&times;</span>
                  <table id="editTbl" border ="1" width="500" align="center">
                     <tr bgcolor="00FF7F">
                     <th><b>Author</b></th>
                     <th><b>Category Name</b></th>
                     <th><b>Name</b></th>
                     <th><b>File</b></th>
                     </tr>
                     <tr>
                     <td><input id="1" type="text" name="Author"</td>
                     <td><input id="2" type="text" name="Category"</td>
                     <td><input id="3" type="text" name="Name"</td>
                     <td><input id="4" type="file" name="File"</td>
                     <td><input id="5" id="submitEdit" type="button" value="edit"onClick="editRequest(this)"></td>
                     </tr>
                  </table>
                </div>
              </div>


      <div>
          <FORM METHOD="GET" ACTION="/Admin/adminHome" align="center">
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
                <tr colspan="3">
                <input  id="add-book" type="button" value="Add book">
                </tr>
            </table>
          <P><INPUT TYPE="SUBMIT" VALUE="Submit" NAME="B1"></P>
          </FORM>
      </div>

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
                <td><input data-id="<%=book.id%>" value="download" onClick="downloadBook(this)"></td>
                <td><input id="edit-button" type="button" value="Edit" data-bookid="<%=book.id%>"></td>
                <td><input id="delete-button" type="button" value="Delete" data-bookid="<%=book.id%>"></td>
            </tr>
            <%}%>
        </table>
<% } %>

    </body>

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

                // Get the modal
                var modal = document.getElementById("Modal");

                // Get the button that opens the modal
                var btn = document.getElementById("add-book");

                // Get the <span> element that closes the modal
                var span = document.getElementsByClassName("close")[0];

                // When the user clicks the button, open the modal
                btn.onclick = function() {
                  modal.style.display = "block";
                }

                // When the user clicks on <span> (x), close the modal
                span.onclick = function() {
                  modal.style.display = "none";
                }

                // When the user clicks anywhere outside of the modal, close it
                window.onclick = function(event) {
                  if (event.target == modal) {
                    modal.style.display = "none";
                  }
                }

                var editForm=document.getElementById("Edit");
                var editSpan=document.getElementById("edit-span");
                var editBtn= document.getElementById("edit-button");
                editBtn.onclick = function(element) {
                                  var editTbl=document.getElementById("editTbl");
                                  var i = document.createElement("input");
                                  i.id="6";
                                  i.type = "hidden";
                                  i.name = "book-id";
                                  i.value = editBtn.getAttribute("data-bookid");
                                  editTbl.appendChild(i);
                                  editForm.style.display = "block";
                                }

                editSpan.onclick = function() {
                                  editForm.style.display = "none";
                                }

                 window.onclick = function(event) {
                     if (event.target == editForm) {
                     editForm.style.display = "none";
                      }}
                  var editSubmit= document.getElementById("submitEdit");
                  
                   function editRequest (element) {
                                  var Author = document.getElementById("1").value;
								  var Category = document.getElementById("2").value;
								  var Name = document.getElementById("3").value;
								  var File = document.getElementById("4").files[0];
								  var bookId = document.getElementById("6").value;
								  console.log(File);
                                  var formData = new FormData();
								  formData.append("Author", Author);
								  formData.append("Category", Category);
								  formData.append("Name", Name);
								  formData.append("File", File);
								  formData.append("bookId",bookId);
								  var request = new XMLHttpRequest();
								  let url =  window.location.protocol + "//" + window.location.host+'/Admin/adminHome';
                                  request.open("PUT", url);
                                  request.send(formData);
                                }
                                var deleteBtn=document.getElementById("delete-button");
                                 deleteBtn.onclick = function(element) {
                                 let bookId = deleteBtn.getAttribute('data-bookid');
                                 console.log(bookId);
                                 let url =  window.location.protocol + "//" + window.location.host+'/Admin/adminHome?bookId='+bookId;
                                 let xmlHttp = new XMLHttpRequest();
                                 var formData = new FormData();
                                 formData.append("bookId", bookId);
                                        xmlHttp.open('DELETE', url);
                                        xmlHttp.send(formData);
                                        location.reload();
                                 }

          </script>
</html>


