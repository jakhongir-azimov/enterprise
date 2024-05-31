package com.company.service.impl;

import com.company.repository.DepartmentRepository;
import com.company.service.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    private final DepartmentRepository departmentRepository;





//    public Map<String, Map<String, Double>> getEmployeeCountAndPercentageByDepartment() {
//        List<DepartmentEntity> departments = departmentRepository.findAll();
//        long totalEmployees = departments.stream()
//                .mapToLong(dept -> dept.getEmployees().size())
//                .sum();
//
//        return departments.stream().collect(Collectors.toMap(
//                DepartmentEntity::getName,
//                dept -> {
//                    long count = dept.getEmployees().size();
//                    double percentage = (double) count * 100 / totalEmployees;
//                    Map<String, Double> stats = new HashMap<>();
//                    stats.put("count", (double) count);
//                    stats.put("percentage", percentage);
//                    return stats;
//                }
//        ));
//    }


}
