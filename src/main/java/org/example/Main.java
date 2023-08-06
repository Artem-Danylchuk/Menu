package org.example;

import javax.persistence.*;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Main {
static EntityManagerFactory emf;
static EntityManager em;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            emf = Persistence.createEntityManagerFactory("Menu");
            em = emf.createEntityManager();
            try{
                while (true){
                    System.out.println("1: add new dish");
                    System.out.println("2: add random dish");
                    System.out.println("3: delete dish");
                    System.out.println("4: change price for dish");
                    System.out.println("5: view menu");
                    System.out.println("6: sort dish by price");
                    System.out.println("7: sort dish with discount");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch(s){
                        case "1":
                            addDish(sc);
                            break;
                        case "2":
                            insertRandomDish(sc);
                            break;
                        case "3":
                            deleteDish(sc);
                            break;
                        case "4":
                            changePriceForDish(sc);
                            break;
                        case "5":
                            viewMenu();
                            break;
                        case "6":
                            sortByPrice();
                            break;
                        case "7":
                            onlyWithDiscount();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void addDish(Scanner sc){
        System.out.print("Enter name for new dish - ");
        String dish = sc.nextLine();
        System.out.print("Enter price - ");
        String priceS = sc.nextLine();
        int price = Integer.parseInt(priceS);
        System.out.print("Enter weight - ");
        String weightS = sc.nextLine();
        int  weight = Integer.parseInt(weightS);
        System.out.print("Enter discount - ");
        String discountS = sc.nextLine();
        int discount = Integer.parseInt(discountS);

        em.getTransaction().begin();
        try{
            MenuClient m = new MenuClient(dish,price,weight,discount);
            em.persist(m);
            em.getTransaction().commit();

            System.out.println("Done! Dish have number- "+m.getId());
        } catch (Exception ex){
            em.getTransaction().rollback();
        }
    }

    private static void deleteDish (Scanner sc){
        System.out.print("Enter dish id: ");
        String idS = sc.nextLine();
        Long id = Long.parseLong(idS);

        MenuClient m = em.getReference(MenuClient.class,id);
        if (m == null){
            System.out.println("Dish not found!");
            return;
        }
        em.getTransaction().begin();
        try{
            em.remove(m);
            em.getTransaction().commit();
        } catch (Exception ex){
            em.getTransaction().rollback();
        }
    }

    private static  void changePriceForDish (Scanner sc){
        System.out.print("Enter dish: ");
        String dish = sc.nextLine();

        System.out.print("Enter new price: ");
        String newPriceS = sc.nextLine();
        int newPrice = Integer.parseInt(newPriceS);

        MenuClient m = null;
        try{
            Query query = em.createQuery("SELECT x FROM MenuClient x WHERE x.dish = :dish", MenuClient.class);
            query.setParameter("dish", dish);

            m =(MenuClient) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Dish not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }
        em.getTransaction().begin();
        try{
            m.setPrice(newPrice);
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
        }
    }

    private static void insertRandomDish (Scanner sc){
        System.out.print("Enter dish count: ");
        String countS = sc.nextLine();
        int count = Integer.parseInt(countS);

        em.getTransaction().begin();
        try{
            for (int i=0; i<count;i++){
                MenuClient m = new MenuClient(randomDish(), RND.nextInt(100), RND.nextInt(350),randomDiscount());
                em.persist(m);
            }
            em.getTransaction().commit();
        } catch (Exception ex){
            em.getTransaction().rollback();
        }
    }

    static final String [] DISHES = {"Tort","Tatar","Soup","Coffee","Meat","Fish",};
    static final Random RND = new Random();
    static String randomDish() { return DISHES[RND.nextInt(DISHES.length)];}
    static int randomDiscount(){
        if ((RND.nextInt(100)/2) !=0){
            return RND.nextInt(30);
        }
        return 0;
    }
    private static void viewMenu (){
        Query query = em.createQuery("SELECT m FROM MenuClient m", MenuClient.class);
        List<MenuClient> list = (List<MenuClient>) query.getResultList();

        for (MenuClient m : list ){
            System.out.println(m);
        }
    }

    private static void sortByPrice (){
        System.out.println("1: Sort by hire price");
        System.out.println("2: Sort by lower price");
        System.out.print("-> ");

        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        switch (s){
            case "1" :
                List<MenuClient> list = em.createQuery("SELECT m FROM MenuClient m ORDER BY price DESC", MenuClient.class).getResultList();
                for (MenuClient m : list ) {
                    System.out.println(m);
                }
                break;
            case "2":
                List<MenuClient> list2 = em.createQuery("SELECT m FROM MenuClient m ORDER BY price ASC", MenuClient.class).getResultList();
                for (MenuClient m : list2 ){
                    System.out.println(m);}
                break;
        }

    }

    private static void onlyWithDiscount(){
       List<MenuClient> list = em.createQuery("SELECT m FROM MenuClient m WHERE discount > 0", MenuClient.class).getResultList();
        for (MenuClient m : list ){
            System.out.println(m);

        }
    }



































}