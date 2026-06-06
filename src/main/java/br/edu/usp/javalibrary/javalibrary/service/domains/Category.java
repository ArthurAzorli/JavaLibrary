package br.edu.usp.javalibrary.javalibrary.service.domains;

import java.util.UUID;

public record Category(UUID id, String name) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        final Category other = (Category) obj;
        return this.id == other.id && this.name.trim().equalsIgnoreCase(((Category) obj).name.trim());
    }

}
