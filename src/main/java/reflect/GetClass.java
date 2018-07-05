package reflect;

public class GetClass{
    public static void main(String[] args) {
        Class<?> demo1=null;
        Class<?> demo2=null;
        Class<?> demo3=null;
        try{ 
            demo1=Class.forName(GetClass.class.getName());
        }catch(Exception e){
            e.printStackTrace();
        }
        demo2=new GetClass().getClass();
        demo3=GetClass.class;
        System.out.println("类名称   "+demo1.getName());
        System.out.println("类名称   "+demo2.getName());
        System.out.println("类名称   "+demo3.getName());
    }
}