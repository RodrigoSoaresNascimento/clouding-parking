package br.com.rodrigo.cloudparking.controller;

import br.com.rodrigo.cloudparking.dto.ParkingCreateDTO;
import br.com.rodrigo.cloudparking.dto.ParkingDTO;
import br.com.rodrigo.cloudparking.dto.ParkingUpdateDTO;
import br.com.rodrigo.cloudparking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }


    @GetMapping
    public ResponseEntity<List<ParkingDTO>> findAll(){

        return ResponseEntity.ok(parkingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id){

        return ResponseEntity.ok(parkingService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ParkingDTO> create (@RequestBody ParkingCreateDTO parkingDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.create(parkingDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ParkingUpdateDTO> update (@PathVariable String id ,
                                                    @RequestBody ParkingUpdateDTO parkingUpdateDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(parkingService.update(id, parkingUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete (@PathVariable String id){
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/exit/{id}")
    public ResponseEntity<ParkingDTO> exit (@PathVariable String id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(parkingService.exit(id));
    }
}
