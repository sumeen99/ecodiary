package ecodiary.server.domain.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "manager")
@Builder
public class Admin {

    @Id
    @Column(nullable = false)
    private Long id;
}
