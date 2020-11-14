package com.wind.controller;
        import com.wind.entity.SyncData;
        import com.wind.service.SyncDataService;
        import com.wind.utils.Results;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

@RestController("syncDataController.v1")
@RequestMapping("/syncdatas")
public class SyncDataController{

    @Autowired
    private SyncDataService syncDataService;

    @GetMapping("/start")
    public ResponseEntity<SyncData> detail() throws Exception {
        syncDataService.getData();
        return Results.success();
    }

}
