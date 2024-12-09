package ssafy.StackFlow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.StackFlow.Domain.Product;
import ssafy.StackFlow.Domain.RT;

import java.util.List;

@Repository
public interface RtRepository extends JpaRepository<RT, Long>{

        List<Product> findByProdCodeContaining(String keyword);
    }

