package com.sohezsoft.student.helper

import com.sohezsoft.student.data.repository.structure.exam.Result
import com.sohezsoft.student.data.repository.structure.exam.TimeTable

class HTMLHelper {

    val helper = HelperClass()

    //give the args as table that is table data - <thead><tbody>
    fun getTable(table: String,title:String): String {
        return """
<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="UTF-8">
      <title>Table</title>
   </head>
   <body>
      <head>
         <title>Simple HTML Table</title>
         <style>
            * {
            padding: 0;
            text-align: center;
            }
            .table-container {
            width: 100%;
            overflow-x: scroll;
            }
            table {
            margin:10px;
            border-collapse: collapse;
            width: 100%;
            display : block;
            }
            table, th, td {
            width: 100%;
            padding : 10px;
            margin: 10px;
            }
            tr,th,td{
            border: 1px solid black; 
            }
         </style>
      </head>
      <body>
      <h1>$title</h1>
         <div class="table-container">
            <table>
               $table
            </table>
         </div>
   </body>
</html>
        """.trimIndent()
    }

    fun getTimeTable(listData: MutableList<TimeTable>): String {
        var row = """
            <tr>
            <th>SUBJECT</td>
             <th>DATE</td>
             <th>TIME</td>
            </tr>
             """.trimIndent()

        for (i in listData) {
            row += """
                  <tr>
                    <td>${i.subjectNameCode}</td>
                    <td>${i.date}</td>
                     <td>${i.time}</td>
                </tr>
            """.trimIndent()
        }
        return getTable(row,"Time Table")
    }

    fun getResult(listData: MutableList<Result>): String {

        var row = ""
        var bottomTotalObtain : Double= 0.0
        var bottomTotalMin :Double = 0.0
        var bottomTotalMax :Double = 0.0
        var bottomResult : String = "Pass"

        for (i in listData) {
            //string into MutableList<Double>
            //a[0] -> Obtain, a[1] -> Min , a[2] -> Max
            val intAryPractical = helper.stringToDoubleArray(i.practicalMarks)
            val intAryInternal = helper.stringToDoubleArray(i.internalMarks)

            if (intAryInternal != null && intAryPractical != null) {
                //practicale variable
                val practicalObtain = intAryPractical[0]
                val practicalMin = intAryPractical[1]
                val practicalMax = intAryPractical[2]

                //internal/theory
                val internalObtain = intAryInternal[0]
                val internalMin = intAryInternal[1]
                val internalMax = intAryInternal[2]

                //total
                val totalObtain = internalObtain.plus(practicalObtain)
                val totalMin = internalMin.plus(practicalMin)
                val totalMax = internalMax.plus(practicalMax)

                bottomTotalObtain += totalObtain
                bottomTotalMin += totalMin
                bottomTotalMax += totalMax

                val grade = helper.calculateGrade(totalObtain)
                val result = if(grade == "F"){"fail"}else{"Pass"}

                if(bottomResult == "Pass" && grade == "F"){
                    bottomResult = "fail"
                }

                row += """
                 <tr>
                  <td>${i.subjectName}</td>
                  <td>${practicalObtain}</td>
                  <td>${practicalMin}</td>
                  <td>${practicalMax}</td>
                  
                  <td>${internalObtain}</td>
                  <td>${internalMin}</td>
                  <td>${internalMax}</td>
                  
                  <td>${totalObtain}</td>
                  <td>${totalMin}</td>
                  <td>${totalMax}</td>
                                    
                  <td>$grade</td>
                  <td>$result</td>
                </tr>
        """.trimIndent()
            } else {
                //if somthing error
            }
        }

        val html = """
  <thead>
    <tr>
      <th rowspan="2">Subject</th>
      <th colspan="3">Practical</th>
      <th colspan="3">Theory</th>
      <th colspan="3">Total Marks</th>
      <th rowspan="2">Grade</th>
      <th rowspan="2">Result</th>
    </tr>
    <tr>
      <th>Obtain</th>
      <th>Min</th>
      <th>Max</th>

      <th>Obtain</th>
      <th>Min</th>
      <th>Max</th>

      <th>Obtain</th>
      <th>Min</th>
      <th>Max</th>
    </tr>
  </thead>
  <tbody>
    $row
    <tr>
      <td colspan="7">Toatal</td>
      <td>$bottomTotalObtain</td>
      <td>$bottomTotalMin</td>
      <td>$bottomTotalMax</td>
      <td> </td>
      <td>$bottomResult</td>
    </tr>
  </tbody>
        """.trimIndent()

        return getTable(html,"Result")
    }
}