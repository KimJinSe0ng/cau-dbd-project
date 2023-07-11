package project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GalleryDB {

    public static void main(String[] args) throws ClassNotFoundException, SQLException{

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/gallery?useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
                "useLegacyDatetimeCode=false&serverTimezone=UTC", "ID", "PW");
        Statement stmt= conn.createStatement();

        System.out.println("프로그램 시작");
        Scanner input = new Scanner(System.in);
        int funcNum;						// 사용자가 선택한 메뉴 번호

        while(true) {
            System.out.println("\n1. 장르와 제작연도를 이용한 작가 검색 \n2. NFT 작품을 특정 가상화폐로 N개 이상 주문한 고객 검색 \n3. 종료");
            System.out.print("메뉴 입력 -> ");
            funcNum = input.nextInt();		// 사용자가 선택한 메뉴 번호를 입력 받음

            if(funcNum == 1) {		// 1. 장르와 제작연도를 이용한 작가 검색
                System.out.print("장르를 입력하시오: ");
                String gen = input.next();		// 장르 입력
                System.out.print("제작연도를 입력하시오(예시: 18th, 19th): ");
                String cen = input.next();		// 제작연도 입력

                try {
                    String sql = "select distinct a_name from artist where a_id in (select a_id "
                            + "from artwork where production_year = '" + cen + "' and genre = '" + gen + "')";
                    ResultSet rset= stmt.executeQuery(sql);
                    while (rset.next()) {
                        System.out.println("Artist name: "+ rset.getString(1));		// 해당 작가 이름 출력
                    }

                }catch(SQLException sqle) {
                    System.out.println("SQLException: "+sqle);
                }


            }else if(funcNum == 2) {	// 2. NFT 작품을 특정 가상화폐로 N개 이상 주문한 고객 검색
                System.out.print("검색하고자 하는 가상화폐: ");
                String coin = input.next();		// 가상화폐 입력
                System.out.print("몇 작품을 구매한 고객을 검색하시겠습니까? ");
                int n = input.nextInt();		// 작품 구매 횟수 입력

                try {
                    String sql = "select distinct c_name from customer where c_id in ( select c_id "
                            + "from nft_order group by c_id, coin_type having count(c_id) >= '"+ n +"' and coin_type = '" + coin + "')";
                    ResultSet rset= stmt.executeQuery(sql);
                    while (rset.next()) {
                        System.out.println("Customer name: "+ rset.getString(1)); 		// 고객 이름 출력
                    }

                }catch(SQLException sqle) {
                    System.out.println("SQLException: "+sqle);
                }


            }else if(funcNum == 3) {	// 3. 종료
                System.out.println("프로그램 종료");
                break;
            }

        }

        stmt.close();
        conn.close();


    }

}
