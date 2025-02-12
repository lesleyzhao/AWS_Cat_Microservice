package com.lesleyzh.cat_demo.service;


import com.lesleyzh.cat_demo.dto.CatBreed;
import com.lesleyzh.cat_demo.dto.CatDto;
import com.lesleyzh.cat_demo.dto.CatFilter;
import com.lesleyzh.cat_demo.dto.ImmutableCatDto;
import com.lesleyzh.cat_demo.model.CatModel;
import com.lesleyzh.cat_demo.repository.CatRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //标记成spring里的一个bean放入IOC container，在其他class里可以用@Autowired来引用
public class CatService {
    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Cacheable(value = "cat_info", key = "#catId") //直接用spring自带的最简单的cacheManager
    //如果需要cache特别复杂的逻辑 就不推荐用spring自带的cache
    public CatDto fetchCatInfo(int catId){
        CatModel catModel = catRepository.findById(catId).orElse(null);
        if(catModel == null){
            return null;
        }

        return ImmutableCatDto.builder()
                .name(catModel.getName())
                .ageYear(catModel.getAge())
                .breed(CatBreed.valueOf(catModel.getBreed()))
                .build();

    }

    public void createCat(CatDto catDto){
        CatModel catModel = new CatModel();
        catModel.setName(catDto.getName());
        catModel.setAge(catDto.getAgeYear());
        catModel.setBreed(catDto.getBreed().name());
        catRepository.save(catModel);
    }

    public List<CatDto> fetchAllCats(int page, int size){
        return List.of(
                ImmutableCatDto.builder()
                        .name("Whiskers")
                        .ageYear(3)
                        .breed(CatBreed.SIAMESE)
                        .build(),
                ImmutableCatDto.builder()
                        .name("Whisker")
                        .ageYear(4)
                        .breed(CatBreed.SIAMESE)
                        .build()
        );
    }

    public List<CatDto> fetchAllCatsWithFilter(CatFilter filter) {
        return List.of(
                ImmutableCatDto.builder()
                        .name("Whiskers")
                        .ageYear(3)
                        .breed(CatBreed.SIAMESE)
                        .build(),
                ImmutableCatDto.builder()
                        .name("Whisker")
                        .ageYear(4)
                        .breed(CatBreed.SIAMESE)
                        .build()
        );
    }
}
