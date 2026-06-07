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

async function handleInteraction() {
    try {
        const response = await fetch('/api/pets/interact/prank', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                petId: 'capy-01', 
                sessionId: currentSessionId 
            })
        });

        // Xử lý các lỗi từ Server (ví dụ: 403 Forbidden - Bước 5.1.2)
        if (!response.ok) {
            const errorMessage = await response.text();
            console.warn("Server từ chối yêu cầu:", errorMessage);
            return; // Dừng hàm tại đây nếu bị chặn
        }

        const data = await response.json();

        // 5.3: Kích hoạt hiệu ứng Fade-to-black
        if (data.triggerBlackScreen) {
            document.getElementById('black-overlay').style.display = 'block';
            document.getElementById('black-overlay').classList.add('fade-in');
        }

        // 5.4: Thay đổi hình ảnh sang "Penalty"
        if (data.isPenalty) {
            const petImg = document.getElementById('pet-img');
            petImg.src = '/assets/images/penalty_pet.png';
            petImg.style.display = 'block';
        }   

        // 6.1: Hiển thị thông báo thua
        if (data.gameOver) {
            alert(data.message);
            // 6.4: Hiện nút chơi lại
            const resetBtn = document.getElementById('reset-btn');
            if (resetBtn) {
                resetBtn.style.display = 'block';
            }
        }
    } catch (error) {
        console.error("Lỗi kết nối:", error);
    }
}

function resetGame() {
    // Reset biến logic
    suspicion = 0;
    document.getElementById('suspicion-level').innerText = "0";

    // Reset giao diện
    document.getElementById('pet-image').src = 'assets/images/tho.jpg';
    document.getElementById('prank-btn').disabled = false;
    document.getElementById('black-screen').style.display = 'none';

    // Reset các mảng hoặc bộ đếm thời gian nếu có
    clearInterval(interactionTimer); 
}