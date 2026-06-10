package config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

// MyBatis SqlSessionFactory를 관리하는 싱글톤 팩토리 클래스
public class MyBatisConnectionFactory {
    
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 클래스패스 기준으로 mybatis-config.xml 파일을 읽어옵니다.
            String resource = "config/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            System.err.println("MyBatis 설정 파일을 읽어오는 중 에러 발생!");
            e.printStackTrace();
            throw new RuntimeException("MyBatis SqlSessionFactory 초기화 실패", e);
        }
    }

    // SqlSessionFactory 인스턴스 자체를 반환받고 싶을 때 사용
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    // 데이터베이스 CRUD 세션을 얻고 싶을 때 사용
    // 파라미터로 true를 전달하여 autoCommit(자동 커밋)을 활성화합니다. (JDBC와 동일하게 작동)
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }
}
