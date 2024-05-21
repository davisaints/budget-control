package budget.control.project.controller;

import budget.control.project.dto.RevenueDTORequest;
import budget.control.project.dto.RevenueDTOResponse;
import budget.control.project.service.RevenueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "revenues")
public class RevenueController {
    @Autowired
    RevenueService revenueService;

    @PostMapping
    public ResponseEntity<RevenueDTOResponse> postRevenue(@RequestBody @Valid RevenueDTORequest revenueDTORequest) {
        return new ResponseEntity<>(revenueService.post(revenueDTORequest), HttpStatus.CREATED);
    }
}
