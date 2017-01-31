# MyRegExApp
This project is just a basic text extractor that require the certain format i have made. the result will create some file, 
some column in each file, and some data for each file.

There are 3 text box, and each text box will have different function after extracting.

Here the text format :

1. <b>Event Name TextBox</b><br>
   <b>Format</b> : T001.012.3 <br>
   <b>Description</b> : <br>
   the function of this part is create file with different id of file<br>
   <b>Detail</b> : <br>
            T001 stand for file name <br>
            012 stand for file name id (ORAGIT, PRC_SSP, EXAMPLE) <br>
            3 stand for number of file that will be created <br>
            
2. <b>Event Short Description</b><br>
   <b>Format</b> : <br>0.3.123&#8727;1.5.73825&#8727;2.691725<br>
   <b>Description</b> : <br>the function of this part is create column name in each file, and will macth id of file name<br>
   <b>Detail</b> : <br>0 stand for id of file<br>
            3 stand for number of column<br>
            123 stand for id of column name<br>
            
3. <b>Event Long Description</b> <br>
   <b>Format</b> : <br>A.T001.2000|B.T002.300|C.T003.2000*T001.23.5.200000.39999|T002.4.60.2005000.400187|*T001.45.23.Test.400.5000000<br>
   <b>Description</b> : <br>the function of this part is for the data that we provide for each file<br>
   <b>Detail</b> : <br>each data are marked with "|" symbol<br>
            each data for each file are marked "*" symbol<br>


