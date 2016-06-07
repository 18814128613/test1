package com.example.administrator.classdesign.bean;

/**
 * Created by Administrator on 2016/5/10.
 */
public class Picture {          //创建Picture类
    private String title;                 //定义字符串，表示图像标题
    private int imageId;                 //定义int变量，表示图像的二进制值
    public Picture(){             //创建默认构造函数
        super();
    }
    public Picture(String title,int imageId){       //定义有参构造函数
        super();
        this.title=title;                     //为图像标题赋值
        this.imageId=imageId;                 //为图像的二进制值赋值
    }
    public String getTitle(){
        return title;                       //定义图像标题的可读属性
    }
    public void setTitle(String title){                  //定义图像标题的可写属性
        this.title=title;
    }
    public int getImageId(){           //定义图像二进制值的可读属性
        return imageId;
    }
    public void setImageId(int imageId){         //定义图像二进制值的可写属性
        this.imageId=imageId;
    }
}
