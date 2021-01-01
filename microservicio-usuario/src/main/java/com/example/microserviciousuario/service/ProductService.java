package com.example.microserviciousuario.service;

import com.example.microserviciousuario.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    public List<Product> getAll(){
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        p1.setPid(1);
        p1.setProductName("PreBoda");
        p1.setPrice((double) 1000);
        p1.setImg("https://cdn0.matrimonio.com.pe/img_e_111712/1/7/1/2/t10_2x_f-52_11_111712.jpg");
        p1.setDescrip("monto de RD$"+p1.getPrice());

        Product p2 = new Product();
        p2.setPid(2);
        p2.setProductName("Boda");
        p2.setPrice((double) 5000);
        p2.setImg("https://trucoslondres.com/wp-content/uploads/2017/06/boda_expres.jpg");
        p2.setDescrip("monto de RD$"+p2.getPrice());

        Product p3 = new Product();
        p3.setPid(3);
        p3.setProductName("Cumpleaños");
        p3.setPrice((double) 3000);
        p3.setImg("https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/1200/public/media/image/2018/01/283709-crear-tarjetas-felicitar-cumpleanos.jpg?itok=qXuZlOOZ");
        p3.setDescrip("monto de RD$"+p3.getPrice());

        Product p4 = new Product();
        p4.setPid(4);
        p4.setProductName("Vídeo de evento");
        p4.setPrice((double) 4000);
        p4.setImg("https://www.stringnet.pe/blog/wp-content/uploads/2017/07/live-streaming-evento-corporativo.jpg");
        p4.setDescrip("monto de RD$"+p4.getPrice());
        
        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        
        
        
        
        return products;
        
    }
}
