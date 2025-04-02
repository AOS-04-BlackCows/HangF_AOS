package com.compose.hangf_aos.Intent

import android.util.Log
import java.sql.*

class DatabaseHelper {
    companion object {
        private const val DB_URL = "jdbc:mariadb://blackcows.iptime.org:3306/HangF_db?useSSL=false"
//        private const val DB_URL = "jdbc:mariadb://192.168.0.7:3306/HangF_db"
        private const val DB_USER = "android_user"
        private const val DB_PASSWORD = "0709"

        fun fetchData(): List<String> {
            val dataList = mutableListOf<String>()
            var connection: Connection? = null

            try {
                // 1. 드라이버 로드
                Class.forName("org.mariadb.jdbc.Driver")

                // 2. DB 연결
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)

                // 3. 쿼리 실행
                val statement: Statement = connection.createStatement()
                val resultSet: ResultSet = statement.executeQuery("SELECT * FROM Customer")

                // 4. 결과 처리
                while (resultSet.next()) {
                    val data = resultSet.getString("name")
                    dataList.add(data)
                }
            } catch (e: Exception) {
                Log.d("DB_TEST", "error : ${e.message}")
                e.printStackTrace()
            } finally {
                // 5. 연결 종료
                connection?.close()
            }
            return dataList
        }
    }
}
