import static java.lang.Math.*;

class main {
    public final static double half = PI/2;
    public static int XlengthOfMap=100;
    public static int YlengthOfMap=100;
    public static int[][] map = new int[XlengthOfMap][YlengthOfMap];

    //default
    public static point inspectRect0(double x,double y,double dir){
        /////����� ������� ��� 0,5PI 1.5 ���

        //validate input
        if(dir>3*half){
            dir -= 2*PI;
        }
        if(dir<-half){
            dir += 2*PI;

        }
        ////for y = Integer


        //
        double finX=0;
        double finY=0;
        int side=0;
        //Left
        if(dir > -half&dir<half){
            side = 0;

            finX=floor(x);
            finY=y+ (x-finX)*tan(dir);
            if(finY<floor(y)){
                side=3;

                finY=floor(y);
                finX=x+(y-floor(y))/tan(dir);
            }else{
                if(finY>ceil(y)){
                    side=1;

                    finY=ceil(y);
                    finX=x-(ceil(y)-y)/tan(dir);
                }
            }
        }else{
            //Right
            if(dir > half&dir<3*half){
                side=2;

                finX=ceil(y);
                finY=y+ (finX-x)*(-tan(dir));
                if(finY<floor(y)){
                    side=3;

                    finY=floor(y);
                    finX=x-(y-floor(y))/(-tan(dir));
                }else{
                    if(finY>ceil(y)){
                        side=1;

                        finY=ceil(x);
                        if(finY==y){finY++;}
                        finX=x+(ceil(x)-y)/(-tan(dir));
                    }
                }
            }
        }
        //System.out.println(finX+":"+finY);
        return new point(finX,finY,dir,side);
    }
    //from decompiler
    public static point inspectRect(double x, double y, double dir)
    {
        if (dir > 4.71238898038469D) {
            dir -= 6.283185307179586D;
        }
        if (dir < -1.5707963267948966D) {
            dir += 6.283185307179586D;
        }
        double ceiling = Math.ceil(y);
        if (y == ceiling) {
            ceiling += 1.0D;
        }
        double ceilingX = Math.ceil(x);
        if (x == ceilingX) {
            ceilingX += 1.0D;
        }
        double floorY = Math.floor(y);
        if (y == floorY) {
            floorY -= 1.0D;
        }
        double floorX = Math.floor(x);
        if (x == floorX) {
            floorX -= 1.0D;
        }
        double finX = 0.0D;
        double finY = 0.0D;
        int side = 0;
        if (((dir > -1.5707963267948966D ? 1 : 0) & (dir < 1.5707963267948966D ? 1 : 0)) != 0)
        {
            side = 0;

            finX = floorX;
            finY = y + (x - finX) * Math.tan(dir);
            if (finY < floorY)
            {
                side = 3;

                finY = floorY;
                finX = x + (y - floorY) / Math.tan(dir);
            }
            else if (finY > ceiling)
            {
                side = 1;

                finY = ceiling;
                finX = x - (ceiling - y) / Math.tan(dir);
            }
        }
        else if (((dir > 1.5707963267948966D ? 1 : 0) & (dir < 4.71238898038469D ? 1 : 0)) != 0)
        {
            side = 2;

            finX = ceilingX;
            finY = y + (finX - x) * -Math.tan(dir);
            if (finY < floorY)
            {
                side = 3;

                finY = floorY;
                finX = x - (y - floorY) / -Math.tan(dir);
            }
            else if (finY > ceiling)
            {
                side = 1;

                finY = ceiling;
                finX = x + (ceiling - y) / -Math.tan(dir);
            }
        }
        return new point(finX, finY, dir, side);
    }
    /*public static point inspectNextRect(point p){
        double finX=0;
        double finY=0;
        switch(p.side){
        case(0):
            finX=p.x-1;
            finY= p.y+tan(p.dir);
            if(finY>ceil(p.y)){
                p.side=1;
                finY= ceil(p.y);
                finX=p.x+(finY-p.y)/tan(p.dir);
            }else{
                if(finY<floor(p.y)){
                    p.side=3;
                    finY= floor(p.y);
                    finX=p.x+ (p.y-finY)/tan(p.dir);
                }
            }
        break;
        case(1):
            finY=p.y+1;
            finX=p.x - 1/tan(p.dir);
            if(finX<floor(p.x)){
                p.side=0;
                finX=floor(p.x);
                finY=p.y+(p.x-finX)*tan(p.dir);
            }else{
                if(finX>ceil(p.x)){
                    p.side=2;
                    finX=ceil(p.x);
                    finY=p.y-(finX-p.x)*tan(p.dir);
                }
            }
        break;
        case(2):
            finX=p.x+1;
           finY= p.y-tan(p.dir);
           if(finY>ceil(p.y)){
               p.side=1;
               finY= ceil(p.y);
               finX=p.x-(finY-p.y)/tan(p.dir);
           }else{
               if(finY<floor(p.y)){
                   p.side=3;
                   finY= floor(p.y);
                   finX=p.x- (p.y-finY)/tan(p.dir);
               }
           }
            break;
        case(3):
            ////dev
            finY=p.y-1;
           finX=p.x + 1/tan(p.dir);
           if(finX<floor(p.x)){
               p.side=0;
               finX=floor(p.x);
               finY=p.y-(p.x-finX)*tan(p.dir);
           }else{
               if(finX>ceil(p.x)){
                   p.side=2;
                   finX=ceil(p.x);
                   finY=p.y+(finX-p.x)*tan(p.dir);
               }
           }
            break;
        }
        p.x=finX;
        p.y=finY;
        return p;
    }*/
    public static WallEnterance inspectWithTexture(double x,double y,double dir,int depth){
        double tempX = x;
        double tempY = y;
        double result = 0;
        int texture=0;
        point tempPoint;
        double correction=0;

        for(int i=0;i<depth;i++){
            tempPoint = inspectRect(tempX,tempY,dir);
            tempX = tempPoint.x;
            tempY = tempPoint.y;
            switch(tempPoint.side){
                case(0):
                    texture = checkMap((int)tempX-1,(int)tempY);
                    correction = abs((int)tempY-tempY);
                    //tempX -= 0.0000001;
                    break;
                case(1):
                    texture = checkMap((int)tempX,(int)tempY);
                    correction = abs((int)tempX-tempX);
                    //tempY += 0.0000001;
                    break;
                case(2):
                    texture = checkMap((int)tempX,(int)tempY);
                    correction = abs(tempY-(int)tempY);
                    //tempX += 0.0000001;
                    break;
                case(3):
                    texture = checkMap((int)tempX,(int)tempY-1);
                    correction = abs(tempX-(int)tempX);
                    //tempY -= 0.0000001;
                    break;
            }
            if(texture!=0){
                break;
            }

        }
        if(texture!=0){
            result = sqrt( pow(tempX-x,2)+pow(tempY-y,2));}
        else{
            result = 100;
        }


        return new WallEnterance(result,correction,dir,texture);
    }
    public static double inspect(double x,double y,double dir,int depth){
        double tempX = x;
        double tempY = y;
        double result = 0;
        int texture=0;
        point tempPoint;


        for(int i=0;i<depth;i++){
            tempPoint = inspectRect(tempX,tempY,dir);
            tempX = tempPoint.x;
            tempY = tempPoint.y;
            switch(tempPoint.side){
                case(0):
                    texture = checkMap((int)tempX-1,(int)tempY);
                    //tempX -= 0.0000001;
                    break;
                case(1):
                    texture = checkMap((int)tempX,(int)tempY);
                    //tempY += 0.0000001;
                    break;
                case(2):
                    texture = checkMap((int)tempX,(int)tempY);
                    //tempX += 0.0000001;
                    break;
                case(3):
                    texture = checkMap((int)tempX,(int)tempY-1);
                    //tempY -= 0.0000001;
                    break;
            }
            if(texture!=0){
                break;
            }

        }
        if(texture!=0){
            result = sqrt( pow(tempX-x,2)+pow(tempY-y,2));}
        else{
            result = 100;
        }
        return result;
    }
    public static int checkMap(int x,int y){
        if(x<XlengthOfMap&&y<YlengthOfMap&&x>=0&&y>=0){

            return map[x][y];}
        else{
            return 0;
        }
    }
    public static point moveToPoint(double x,double y,double dir,double length){
        point result = new point(x,y,dir,-1);
        x = -length*cos(dir)+result.x;
        y = length*sin(dir)+result.y;
        if(checkMap((int)x,(int)y) == 0){
            result.x=x;
            result.y=y;
        }else{
            result.side=-2;
        }

        return result;
    }
    public static double testInspect(double x,double y,double dir,int depth){
        point tempPoint=null;
        double tempX=x;
        double tempY=y;
        double result = 100000;
        for(int i =0;i<100;i++){
            tempPoint=moveToPoint(tempX,tempY,dir,0.5);
            if(tempPoint.side==-2){
                for(int i2 =0;i2<10;i2++){
                    tempPoint = moveToPoint(tempX,tempY,dir,0.05);
                    if(tempPoint.side==-2){
                        break;
                    }
                    tempX=tempPoint.x;
                    tempY=tempPoint.y;

                }
                for(int i2 =0;i2<10;i2++){
                    tempPoint = moveToPoint(tempX,tempY,dir,0.005);
                    if(tempPoint.side==-2){
                        break;
                    }
                    tempX=tempPoint.x;
                    tempY=tempPoint.y;

                }
                tempPoint= inspectRect(tempX,tempY,dir);

                tempX=tempPoint.x;
                tempY=tempPoint.y;
                result=sqrt(pow(x-tempX,2)+pow(y-tempY,2));
                break;
            }
            tempX=tempPoint.x;
            tempY=tempPoint.y;
        }
        return result;

    }
    public static WallEnterance FishEye(WallEnterance fullLenght,double CentralDir){
        double angle = CentralDir;
        fullLenght.length = sin(angle)*fullLenght.length;
        return fullLenght;
    }
    public static double DebugInspect(double x,double y,double dir,int depth){
        point tempPoint = inspectRect(x,y,dir);



        return sqrt(pow(x-tempPoint.x,2)+pow(y-tempPoint.y,2));
    }
}
