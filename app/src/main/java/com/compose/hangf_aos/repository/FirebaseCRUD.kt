package com.compose.hangf_aos.repository

import android.util.Log
import androidx.compose.runtime.Composable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun Greeting(docName: String, owders: String) {
    val db = Firebase.firestore

    //Customer 데이터 추가
    val customer = hashMapOf(
        "name" to "홍길동",
        "phoneNumber" to "01012345678"  // PK 역할
    )

    db.collection("customers")
        .document(customer["phoneNumber"] as String) // 전화번호를 Document ID로 사용
        .set(customer)
        .addOnSuccessListener {
            Log.d("Firestore", "고객 추가 성공")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "고객 추가 실패", e)
        }

    //Store 데이터 추가
    val store = hashMapOf(
        "name" to "스타벅스 강남점",
        "address" to "서울 강남구 테헤란로 123",
        "phoneNumber" to "027890123",
        "dayOnTime" to listOf(
            mapOf("week" to "Monday", "openTime" to "08:00", "closeTime" to "22:00"),
            mapOf("week" to "Tuesday", "openTime" to "08:00", "closeTime" to "22:00")
        )
    )

    db.collection("stores")
        .add(store)  // Firestore가 자동으로 ID 생성
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "매장 추가 성공! ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "매장 추가 실패", e)
        }

    //Menu 데이터 추가
    val menu = hashMapOf(
        "storeId" to "generated_store_id",
        "name" to "아메리카노",
        "picture" to "menu_image_url",
        "description" to "진한 원두의 맛",
        "price" to 4500,
        "isActive" to true
    )

    db.collection("menus")
        .add(menu)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "메뉴 추가 성공! ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "메뉴 추가 실패", e)
        }

    //Order 데이터 추가
    val order = hashMapOf(
        "storeId" to "generated_store_id",
        "customerId" to "generated_customer_id",
        "customerName" to "홍길동",
        "userPhoneNumber" to "01012345678",
        "menuOrders" to listOf("menu_order_1", "menu_order_2"),  // MenuOrder ID 리스트
        "totalPrice" to 9000,
        "status" to "Pending",
        "pickUpTime" to "2025-04-02T14:00:00"
    )

    db.collection("orders")
        .add(order)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "주문 추가 성공! ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "주문 추가 실패", e)
        }

    //특정 Store의 메뉴 리스트 가져오기
    db.collection("menus")
        .whereEqualTo("storeId", "generated_store_id")
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("Firestore", "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "메뉴 목록 가져오기 실패", e)
        }

    //특정 고객의 주문 리스트 가져오기
    db.collection("orders")
        .whereEqualTo("customerId", "generated_customer_id")
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("Firestore", "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "주문 목록 가져오기 실패", e)
        }

    //특정 주문의 상태 업데이트 (예: 'Accepted'로 변경)
    val orderRef = db.collection("orders").document("order_id")

    orderRef.update("status", "Accepted")
        .addOnSuccessListener {
            Log.d("Firestore", "주문 상태 업데이트 성공!")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "주문 상태 업데이트 실패", e)
        }
}

