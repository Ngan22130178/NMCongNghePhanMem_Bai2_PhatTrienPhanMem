async function selectPet(id) {
    try {
        const response = await fetch(`/api/pets/${id}`);
        if (!response.ok) throw new Error("Không tìm thấy thú cưng");
        
        const pet = await response.json();
        
        // Cập nhật tên
        document.getElementById('display-name').innerText = "Bạn đang chơi với: " + pet.name;
        
        // Cập nhật ảnh: Nối /assets/images/ với tên file từ DB (vd: capy.png)
        const img = document.getElementById('pet-img');
        img.src = `/assets/images/${pet.imagePath}`; 
        
        img.style.display = 'block';
        
        // Âm thanh (nếu có)
        if (pet.voiceSfx) {
            new Audio(`/assets/audio/${pet.voiceSfx}`).play().catch(() => {});
        }
    } catch (error) {
        console.error("Lỗi hiển thị:", error);
    }
}

function useTool(toolId) {
    const stage = document.getElementById('pet-stage');
    
    // Xóa sạch class cũ
    stage.classList.remove('cursor-comb', 'cursor-hand');

    if (toolId === 'comb') {
        stage.classList.add('cursor-comb');
        console.log("Đã chọn: Dụng cụ (icon mặc định)");
    } else if (toolId === 'hand') {
        stage.classList.add('cursor-hand');
        console.log("Đã chọn: Bàn tay (icon mặc định)");
    }
}