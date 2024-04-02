class Badge {
    public String print(Integer id, String name, String department) {
        String s="";
        if(id != null && department != null){
            
        s= "["+ id + "] - "+name +" - "+department.toUpperCase();}
        else if(id == null && department != null){
            s= name + " - "+department.toUpperCase();
        }
        else if( department == null){
            if(id==null){
                s= name + " - "+"OWNER";
            }
            else{
            s= "["+ id + "] - "+name +" - "+"OWNER";
            }
        
        }
        return s;
    }
}
