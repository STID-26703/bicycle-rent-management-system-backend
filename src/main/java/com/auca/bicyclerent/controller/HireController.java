package com.auca.bicyclerent.controller;

import com.auca.bicyclerent.entity.Hire;
import com.auca.bicyclerent.entity.HireStatus;
import com.auca.bicyclerent.service.HireService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/hires")
public class HireController {

    private final HireService hireService;

    public HireController(HireService hireService) {
        this.hireService = hireService;
    }

    // A & D1
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Hire hire) {
        String res = hireService.saveHire(hire);
        if (res.equals("Hire saved successfully")) {
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else if (res.equals("Hire date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        } else if (res.equals("Bicycle is already hired on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Hire>> getAll() {
        return ResponseEntity.ok(hireService.getAllHires());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Hire> opt = hireService.getHireById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The hire with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Hire hire) {
        String res = hireService.updateHire(id, hire);
        if (res.equals("Hire updated successfully")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String res = hireService.deleteHire(id);
        if (res.equals("Hire deleted successfully")) {
            return ResponseEntity.ok(res);
        } else if (res.equals("That hire has return charges, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    // B2 DERIVED
    @GetMapping("/by-status")
    public ResponseEntity<List<Hire>> getByStatus(@RequestParam HireStatus status) {
        return ResponseEntity.ok(hireService.getHiresByStatus(status));
    }

    // B3 DERIVED
    @GetMapping("/between")
    public ResponseEntity<List<Hire>> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(hireService.getHiresBetween(startDate, endDate));
    }

    // D2
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam HireStatus status) {
        String res = hireService.updateStatus(id, status);
        if (res.equals("Hire status updated successfully")) {
            return ResponseEntity.ok(res);
        } else if (res.equals("The hire with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
    }

    // F1 JPQL
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return ResponseEntity.ok(hireService.getStatsByStatus());
    }

    // F2 JPQL
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID bicycleId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        String res = hireService.cancelDay(bicycleId, localDate);
        return ResponseEntity.ok(res);
    }

    // BONUS
    @GetMapping("/page")
    public ResponseEntity<Page<Hire>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "hireDate,desc") String sort) {
        Sort sortObj = Sort.by(Sort.Direction.DESC, "hireDate");
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.split(",");
            String prop = parts[0].trim();
            Sort.Direction dir = (parts.length > 1 && parts[1].trim().equalsIgnoreCase("asc"))
                    ? Sort.Direction.ASC : Sort.Direction.DESC;
            sortObj = Sort.by(dir, prop);
        }
        Pageable pageable = PageRequest.of(page, size, sortObj);
        return ResponseEntity.ok(hireService.getHiresPage(pageable));
    }
}
