/*package com.softdevcode.ws_finflow.controller;

import com.softdevcode.ws_finflow.entity.Transaction;
import com.softdevcode.ws_finflow.entity.User;
import com.softdevcode.ws_finflow.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final TransactionRepository txRepo;

    public DashboardController(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    @GetMapping
    public Map<String, Object> getDashboard(@RequestHeader("X-User-Id") Integer userId) {
        // Inicio del mes
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        LocalDateTime end = LocalDateTime.now();

        // Traer transacciones del usuario desde inicio del mes
        List<Transaction> transactions = txRepo.findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(userId, start, end);

        double ingresos = transactions.stream()
                .filter(t -> "income".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double egresos = transactions.stream()
                .filter(t -> "expense".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double saldo = ingresos - egresos;

        Map<String, Map<String, Double>> timeline = new TreeMap<>();
        for (Transaction t : transactions) {
            String day = t.getCreatedAt().toLocalDate().toString();
            timeline.putIfAbsent(day, new HashMap<>(Map.of("income",0.0,"expense",0.0)));
            // Acumular si hay varias transacciones del mismo tipo en un día
            Map<String, Double> dayMap = timeline.get(day);
            dayMap.put(t.getType(), dayMap.get(t.getType()) + t.getAmount());
        }

        return Map.of(
                "ingresos", ingresos,
                "egresos", egresos,
                "saldo", saldo,
                "timeline", timeline,
                "transactions", transactions,
                "total_transactions", transactions.size()
        );
    }
    // Para /overview y /summary se hace de forma similar usando streams y groupBy
}
 */

package com.softdevcode.ws_finflow.controller;

import com.softdevcode.ws_finflow.entity.Transaction;
import com.softdevcode.ws_finflow.entity.User;
import com.softdevcode.ws_finflow.repository.TransactionRepository;
import com.softdevcode.ws_finflow.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final TransactionRepository txRepo;
    private final UserRepository userRepository;

    public DashboardController(TransactionRepository txRepo, UserRepository userRepository) {
        this.txRepo = txRepo;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Map<String, Object> getDashboard(Authentication authentication) {
        // Obtener usuario desde el token JWT (ya validado por JwtFilter)
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Integer userId = Math.toIntExact(user.getId());

        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now();

        List<Transaction> transactions = txRepo
                .findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(userId, start, end);

        double ingresos = transactions.stream()
                .filter(t -> "income".equals(t.getType()))
                .mapToDouble(Transaction::getAmount).sum();

        double egresos = transactions.stream()
                .filter(t -> "expense".equals(t.getType()))
                .mapToDouble(Transaction::getAmount).sum();

        Map<String, Map<String, Double>> timeline = new TreeMap<>();
        for (Transaction t : transactions) {
            String day = t.getCreatedAt().toLocalDate().toString();
            timeline.putIfAbsent(day, new HashMap<>(Map.of("income", 0.0, "expense", 0.0)));
            Map<String, Double> dayMap = timeline.get(day);
            dayMap.put(t.getType(), dayMap.get(t.getType()) + t.getAmount());
        }

        return Map.of(
                "ingresos", ingresos,
                "egresos", egresos,
                "saldo", ingresos - egresos,
                "timeline", timeline,
                "transactions", transactions,
                "total_transactions", transactions.size()
        );
    }
}