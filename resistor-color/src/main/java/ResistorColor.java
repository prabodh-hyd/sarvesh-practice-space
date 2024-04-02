class ResistorColor {
    int colorCode(String color) {
        if(color.equals("black")){
            return 0;
        }
       else if(color.equals("brown")){
            return 1;
        }
        else if(color.equals("red")){
            return 2;
        }
        else if(color.equals("orange")){
            return 3;
        }
        else if(color.equals("yellow")){
            return 4;
        }
        else if(color.equals("green")){
            return 5;
        }
        else if(color.equals("blue")){
            return 6;
        }
        else if(color.equals("violet")){
            return 7;
        }
        else if(color.equals("grey")){
            return 8;
        }
        else if(color.equals("white")){
            return 9;
        }
        else{
            return 0;
        }
    }

    String[] colors() {
    String[] s = {"black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white"};
        return s;
    }
}
/*class ResistorColorDuo {
    int value(String[] colors) {
        StringBuilder sb = new StringBuilder();
       
        for(int i=0;i<2;i++){
            if(colors[i].equals("black")){
                sb.append("0");
            }
            else if(colors[i].equals("brown")){
                sb.append("1");
            }
            else if(colors[i].equals("red")){
                sb.append("2");
            }
            else if(colors[i].equals("orange")){
                sb.append("3");
            }
            else if(colors[i].equals("yellow")){
                sb.append("4");
            }
            else if(colors[i].equals("green")){
                sb.append("5");
            }
            else if(colors[i].equals("blue")){
                sb.append("6");
            }
            else if(colors[i].equals("violet")){
                sb.append("7");
            }
            else if(colors[i].equals("grey")){
                sb.append("8");
            }
            else if(colors[i].equals("white")){
                sb.append("9");
            }
            else{
                sb.append("");
            }
        }
        return Integer.parseInt(sb.toString());
    }
}
*/
