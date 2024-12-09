package ren1kron;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ren1kron.dao.PointDao;
import org.ren1kron.models.Point;

public class HibernateExampleTest {
//    private SessionFactory sessionFactory;
//
//    @BeforeEach
//    protected void setUp() {
//        // A SessionFactory is set up once for an application!
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure() // configures settings from hibernate.cfg.xml
//                .build();
//        try {
//            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
//        }
//        catch (Exception e) {
//            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
//            // so destroy it manually.
//            StandardServiceRegistryBuilder.destroy( registry );
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    @AfterEach
//    protected void tearDown() throws Exception {
//        if ( sessionFactory != null ) {
//            sessionFactory.close();
//        }
//    }
//
//
////    @Test
////    public void save_obj_to_the_db(){
////        Point point = new Point(1, 1, 1);
////        try (Session session = sessionFactory.openSession()) {
////            session.beginTransaction();
////
////            //
////            session.persist(point);
////
////
////            session.getTransaction().commit();
////        }
////    }
//    @Test
//    public void clear_all() {
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            var query = session.createQuery("delete from Point");
//            query.executeUpdate();
//
//            session.getTransaction().commit();
//        }
//    }



//    private static PointDao pointDao;
//
//    @BeforeAll
//    protected static void setUp() {
//        pointDao = PointDao.getInstance();
//    }
//
//    @AfterAll
//    protected static void tearDown() {
//        pointDao.close();
//    }
//
//    @Test
//    public void add() {
//        Point point = new Point(1, 1, 1);
//        pointDao.addPoint(point);
//    }
//
//    @Test
//    public void add2() {
//        Point point = new Point(1, 1, 1);
//        pointDao.addPoint(point);
//    }
//
//
//    @Test
//    public void add3() {
//        Point point = new Point(1, 1, 1);
//        pointDao.addPoint(point);
//    }
//    @Test
//    public void clearUserPoints() {
//        pointDao.clearUserPoints();
//    }

}
