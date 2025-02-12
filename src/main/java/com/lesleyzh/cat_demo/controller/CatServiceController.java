package com.lesleyzh.cat_demo.controller;

import com.lesleyzh.cat_demo.dto.CatDto;
import com.lesleyzh.cat_demo.dto.CatFilter;
import com.lesleyzh.cat_demo.ratelimiter.RedissonRateLimiter;
import com.lesleyzh.cat_demo.service.CatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//如果打出@RestController或者其他的Annotation，没有自动补全或者自动import，就需要去build.gradle里看spring的dependency有无成功import
//括号里是api前缀
@RestController()  // 让API方法返回的对象都转化为json文本
@RequestMapping("api/v1/cat")
@Tag(name = "Cat Service", description = "API for managing cats")
//http://localhost:8080/swagger-ui/index.html 可以使用swagger api在网站上用一个UI好的界面测试crud
public class CatServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatServiceController.class);

    //bean injection，方便在当下class调用catservice，从IOC container里拿出这个bean使用。
    // 加不加@Autowired都可以，这里不加autowire是通过constructor function注入
    private final CatService catService;
    private final RedissonRateLimiter rateLimiter;

    public CatServiceController(CatService catService, RedissonRateLimiter rateLimiter) {
        this.catService = catService;
        this.rateLimiter = rateLimiter;
    }

    @GetMapping("/info/{cat-id}")
    @Operation(summary = "Get cat info by id")
    public ResponseEntity<CatDto> getCat(@PathVariable("cat-id") int catId){
        if(!rateLimiter.allowRequest("xxxx")){
            LOGGER.info("Rate Limit Exceeded for cat ID: {}", catId);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        //ResponseEntity就是http status code, 404, 200...
        //<CatDto> 显示ResponseEntity里包含的内容，这个内容会返回给user
        LOGGER.info("Fetching cat info for cat id: " + catId);

        //还可以加一个metric collection，实时检测多少人在用这个function, 连上k8s上，比如当前创建猫咪的人多了就scale up
        //k8s默认只监控cpu和内存scale up，但是创建猫咪不占cpu和内存，所以人多起来时要提前监控
        //Incoming get request metrics
        // getrequest.incr();

        CatDto catDto;

        try{
            catDto = catService.fetchCatInfo(catId);
        }catch(Exception e){
            LOGGER.error("Error fetching cat info", e);
            return ResponseEntity.internalServerError().build();
            //build()生成这个responseentity
        }

        if(catDto == null){
            return ResponseEntity.notFound().build(); //404
        }

        return ResponseEntity.ok(catDto);
    }


    @GetMapping("/all")
    @Operation(summary = "Get all cats with pagination")
    public ResponseEntity<List<CatDto>> getAllCats(@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "10") int size){
        LOGGER.info("Fetching all cats");
        List<CatDto> catDtos;
        try{
            catDtos = catService.fetchAllCats(page, size);
        }catch (Exception e){
            LOGGER.error("Error fetching all cats", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(catDtos);
    }

    @GetMapping("/all/filter")
    @Operation(summary = "Get all cats with filter")
    public ResponseEntity<List<CatDto>> getAllCatsWithFilter(@RequestParam CatFilter filter){
        LOGGER.info("Fetching all cats with filter: " + filter);
        List<CatDto> catDtos;
        try{
            catDtos = catService.fetchAllCatsWithFilter(filter);
        }catch (Exception e){
            LOGGER.error("Error fetching all cats with filter", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(catDtos);
    }


    @PostMapping("/create")
    @Operation(summary = "Create a new cat")
    public ResponseEntity<CatDto> createCat(@RequestBody CatDto catDto){
        try{
            LOGGER.info("Creating cat: " + catDto.getName());
            catService.createCat(catDto);
        }catch(Exception e){
            LOGGER.error("Error creating cat", e);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //@DeleteMapping() delete
//    @DeleteMapping("/delete/{cat-id}")
//    @Operation(summary = "Delete a cat by id")
//    public ResponseEntity<Void> deleteCat(@PathVariable("cat-id") int catId) {
//        try {
//            catService.deleteCat(catId);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//        return ResponseEntity.noContent().build();
//    }
    //@PutMapping() update
}
