package com.br.pi.FishAndChips.Sale;


import com.br.pi.FishAndChips.Product.Product;
import com.br.pi.FishAndChips.Product.ProductService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageRest {

    @Autowired
    private ProductService productService;

    private static Map<Long, byte[]> imageCache = new HashMap<>();

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") Long id) {
        byte[] imageByte = imageCache.get(id);
        if(imageByte == null){
            Product prod = productService.findById(id);

            if (prod != null && prod.getImage() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            imageByte = Base64.decodeBase64(prod.getImage());
            imageCache.put(id, imageByte);
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageByte);
    }

}
