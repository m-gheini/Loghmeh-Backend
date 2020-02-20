package IECA.logic;

public class User {
    private int id;
    private String name;
    private String familyName;
    private String email;
    private int credit;
    private Cart myCart;
    private String phoneNumber;

    public User(int _id, String _name, String _familyName, String _email, int _credit,String _phoneNumber){
        id = _id;
        name = _name;
        familyName = _familyName;
        email =_email;
        credit = _credit;
        phoneNumber = _phoneNumber;
        myCart = new Cart();
    }
    public Cart getMy_cart(){return myCart;}
    public int getId(){return id;}
    public String getName(){return name;}
    public String getFamilyName(){return familyName;}
    public String getEmail(){return email;}
    public String getPhoneNumber(){return phoneNumber;}
    public int getCredit(){return credit;}
    public void setId(int _id){id = _id;}
    public void setName(String _name){name = _name;}
    public void setFamilyName(String _familyName){familyName = _familyName;}
    public void setEmail(String _email){email = _email;}
    public void setCredit(int _credit){credit = _credit;}
    public void setMyCart(Cart _myCart){myCart = _myCart;}
    public void setPhoneNumber(String _phoneNumber){phoneNumber = _phoneNumber;}
    public void addCredit(int value){credit = credit+value;}

}
