# Credenciales de Acceso - Panel de Gestión de Mascotas

## 🔐 Usuario Administrador (Acceso Completo)

**Email:** `admin@peluditosya.com`  
**Contraseña:** `admin123`

### Permisos del Administrador:
- ✅ Acceso completo al panel de gestión de mascotas
- ✅ Visualizar todas las mascotas del sistema
- ✅ Eliminar cualquier mascota
- ✅ Actualizar estado de cualquier mascota
- ✅ Editar cualquier mascota
- ✅ Acceso al panel de administración (`/admin-dashboard`)
- ✅ Gestión de refugios y usuarios

---

## 🏠 Usuario Refugio de Prueba (Acceso Limitado)

**Email:** `refugio@test.com`  
**Contraseña:** `refugio123`  
**Refugio:** Refugio de Prueba

### Permisos del Encargado de Refugio:
- ✅ Acceso al panel de gestión de mascotas
- ⚠️ Solo visualizar mascotas de su propio refugio
- ⚠️ Solo eliminar mascotas de su refugio
- ⚠️ Solo actualizar estado de mascotas de su refugio
- ⚠️ Solo editar mascotas de su refugio
- ✅ Registrar nuevas mascotas en su refugio
- ❌ Sin acceso al panel de administración

---

## 🚀 Instrucciones de Acceso

### 1. Acceder al Sistema
1. Ve a: `http://localhost:4200`
2. Haz clic en **"Iniciar"** en la esquina superior derecha
3. Selecciona **"Iniciar Sesión"**

### 2. Usar Credenciales
- Ingresa el email y contraseña correspondientes
- El sistema detectará automáticamente el tipo de usuario

### 3. Acceder al Panel de Gestión
- **Administradores:** Verán "Gestión de Mascotas" en el menú superior
- **Refugios:** También verán "Gestión de Mascotas" pero con acceso limitado
- **URL directa:** `http://localhost:4200/pets-management`

---

## 🔧 Funcionalidades por Rol

### Panel de Gestión de Mascotas (`/pets-management`)

#### Administrador:
- Badge rojo: "🔧 Administrador"
- Ve todas las mascotas del sistema
- Puede gestionar cualquier mascota
- Todos los botones habilitados

#### Encargado de Refugio:
- Badge azul: "🏠 Encargado de Refugio"
- Ve solo mascotas de su refugio
- Botones deshabilitados para mascotas de otros refugios
- Mensaje: "⚠️ Solo tu refugio" en mascotas no gestionables

---

## ⚠️ Seguridad

- Las credenciales mostradas son **SOLO PARA DESARROLLO**
- En producción, cambiar todas las contraseñas
- El usuario administrador se crea automáticamente al iniciar la aplicación
- Los guards protegen todas las rutas sensibles

---

## 🐕 Probando el Sistema

1. **Login como Admin:** Verifica acceso completo
2. **Login como Refugio:** Verifica limitaciones de acceso
3. **Sin Login:** Intenta acceder a `/pets-management` (debe redirigir a login)
4. **Login como Adopter:** Intenta acceder a `/pets-management` (debe mostrar acceso denegado)

---

*Usuarios creados automáticamente por: `AdminUserInitializer.java`*
