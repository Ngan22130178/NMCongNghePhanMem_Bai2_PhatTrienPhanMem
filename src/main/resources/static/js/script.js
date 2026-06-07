/**
 * ============================================================
 * MY SILLY BESTIE – script.js
 * Frontend JavaScript cho UC3 (Trêu chọc) và UC4 (Chăm sóc)
 * ============================================================
 * Xử lý phía Client:
 *  1. [UC3+UC4] Web Audio API – Tổng hợp âm thanh không cần file .mp3
 *  2. [UC2]     Custom Cursor – Con trỏ đổi emoji khi hover dụng cụ
 *  3. [UC4]     Confetti effect – Hạt vui vẻ khi CARE thành công
 *  4. [UC3]     Screen shake – Rung màn hình khi Penalty
 * ============================================================
 */

// ============================================================
// 1. WEB AUDIO API – Tổng hợp âm thanh trực tiếp từ trình duyệt
//    (Không cần file .mp3 – hoạt động hoàn toàn client-side)
// ============================================================
const AudioCtx = window.AudioContext || window.webkitAudioContext;
let audioCtx = null;

function getAudioCtx() {
    if (!audioCtx) {
        audioCtx = new AudioCtx();
    }
    return audioCtx;
}

/**
 * [UC4 – CHĂM SÓC] Âm thanh dịu dàng kiểu "ding" – nhạc hộp nhỏ
 * Phát khi người chơi dùng dụng cụ CARE (Lược / Máy Sấy).
 * Nốt nhạc: C5 (523Hz) → E5 (659Hz) → G5 (784Hz) → C6 (1047Hz)
 */
function playCareSound() {
    try {
        const ctx = getAudioCtx();
        const notes = [523, 659, 784, 1047]; // C5, E5, G5, C6
        notes.forEach((freq, i) => {
            const osc = ctx.createOscillator();
            const gain = ctx.createGain();
            osc.connect(gain);
            gain.connect(ctx.destination);
            osc.type = 'sine';
            osc.frequency.value = freq;
            const startTime = ctx.currentTime + i * 0.12;
            gain.gain.setValueAtTime(0, startTime);
            gain.gain.linearRampToValueAtTime(0.3, startTime + 0.05);
            gain.gain.exponentialRampToValueAtTime(0.001, startTime + 0.4);
            osc.start(startTime);
            osc.stop(startTime + 0.4);
        });
    } catch (e) { /* bỏ qua nếu trình duyệt chặn */ }
}

/**
 * [UC3 – TRÊU CHỌC] Âm thanh tinh nghịch kiểu "boing"
 * Phát khi người chơi dùng dụng cụ PRANK (Lông Vũ / Bình Xịt).
 * Sawtooth wave từ 300Hz xuống 80Hz – cảm giác trêu chọc
 */
function playPrankSound() {
    try {
        const ctx = getAudioCtx();
        const osc = ctx.createOscillator();
        const gain = ctx.createGain();
        osc.connect(gain);
        gain.connect(ctx.destination);
        osc.type = 'sawtooth';
        osc.frequency.setValueAtTime(300, ctx.currentTime);
        osc.frequency.exponentialRampToValueAtTime(80, ctx.currentTime + 0.4);
        gain.gain.setValueAtTime(0.3, ctx.currentTime);
        gain.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime + 0.4);
        osc.start(ctx.currentTime);
        osc.stop(ctx.currentTime + 0.4);
    } catch (e) { /* bỏ qua */ }
}

/**
 * [UC3 kết thúc – PENALTY] Âm thanh "crash" khi thua
 * Phát khi Suspicion >= 100% và thú cưng nổi giận.
 * Kết hợp: White noise filtered (trống rung) + Square wave (tiếng "wah")
 */
function playPenaltySound() {
    try {
        const ctx = getAudioCtx();
        // Tiếng trống rung – white noise qua low-pass filter
        const bufferSize = ctx.sampleRate * 0.5;
        const buffer = ctx.createBuffer(1, bufferSize, ctx.sampleRate);
        const data = buffer.getChannelData(0);
        for (let i = 0; i < bufferSize; i++) {
            data[i] = (Math.random() * 2 - 1) * Math.pow(1 - i / bufferSize, 2);
        }
        const source = ctx.createBufferSource();
        const gain = ctx.createGain();
        const filter = ctx.createBiquadFilter();
        source.buffer = buffer;
        filter.type = 'lowpass';
        filter.frequency.value = 300;
        source.connect(filter);
        filter.connect(gain);
        gain.connect(ctx.destination);
        gain.gain.setValueAtTime(0.8, ctx.currentTime);
        gain.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime + 0.5);
        source.start(ctx.currentTime);

        // Thêm tiếng "wah" – square wave 200Hz → 50Hz
        const osc2 = ctx.createOscillator();
        const gain2 = ctx.createGain();
        osc2.connect(gain2);
        gain2.connect(ctx.destination);
        osc2.type = 'square';
        osc2.frequency.setValueAtTime(200, ctx.currentTime + 0.1);
        osc2.frequency.exponentialRampToValueAtTime(50, ctx.currentTime + 0.6);
        gain2.gain.setValueAtTime(0.2, ctx.currentTime + 0.1);
        gain2.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime + 0.6);
        osc2.start(ctx.currentTime + 0.1);
        osc2.stop(ctx.currentTime + 0.6);
    } catch (e) { /* bỏ qua */ }
}

/**
 * [UC4 kết thúc – VICTORY] Âm thanh "fanfare" mừng chiến thắng
 * Phát khi Happiness >= 100% và thú cưng cực hạnh phúc.
 * Âm giai điêu vui mừng tăng dần: C5→E5→G5→C6→E6
 */
function playVictorySound() {
    try {
        const ctx = getAudioCtx();
        // Giai điệu dâng cao – C major chord arpeggio
        const notes = [523, 659, 784, 1047, 1319];
        notes.forEach((freq, i) => {
            const osc = ctx.createOscillator();
            const gain = ctx.createGain();
            osc.connect(gain);
            gain.connect(ctx.destination);
            osc.type = 'triangle';
            osc.frequency.value = freq;
            const startTime = ctx.currentTime + i * 0.15;
            gain.gain.setValueAtTime(0, startTime);
            gain.gain.linearRampToValueAtTime(0.35, startTime + 0.06);
            gain.gain.exponentialRampToValueAtTime(0.001, startTime + 0.6);
            osc.start(startTime);
            osc.stop(startTime + 0.7);
        });
        // Bổ sung "sparkle" – cao độ rất cao
        setTimeout(() => {
            const osc2 = ctx.createOscillator();
            const gain2 = ctx.createGain();
            osc2.connect(gain2);
            gain2.connect(ctx.destination);
            osc2.type = 'sine';
            osc2.frequency.setValueAtTime(2093, ctx.currentTime); // C7
            osc2.frequency.exponentialRampToValueAtTime(1397, ctx.currentTime + 0.3); // F6
            gain2.gain.setValueAtTime(0.15, ctx.currentTime);
            gain2.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime + 0.5);
            osc2.start(ctx.currentTime);
            osc2.stop(ctx.currentTime + 0.5);
        }, 750);
    } catch (e) { /* bỏ qua */ }
}

// ============================================================
// 2. TỰ ĐỘNG PHÁT ÂM THANH DỰA TRÊN TRẠNG THÁI GAME
//    lastReaction được inject từ Thymeleaf inline JS trong game.html:
//    const lastReaction = /*[[${gameSession.lastReaction}]]*/ 'idle';
// ============================================================
function initGameAudio() {
    if (typeof lastReaction === 'undefined') return;

    // [UC4] Phát âm thanh chăm sóc khi reaction = "happy"
    if (lastReaction === 'happy') {
        playCareSound();
    }
    // [UC3] Phát âm thanh trêu chọc khi reaction = "suspicious"
    else if (lastReaction === 'suspicious') {
        playPrankSound();
    }
    // [UC3 Penalty] Phát âm thanh crash khi reaction = "angry"
    else if (lastReaction === 'angry') {
        playPenaltySound();
    }
    // [UC4 Victory] Phát âm thanh fanfare khi reaction = "victory"
    else if (lastReaction === 'victory') {
        playVictorySound();
    }
}

// ============================================================
// 3. [UC2] CUSTOM CURSOR – Đổi con trỏ thành icon dụng cụ khi hover
//    Trực quan hóa dụng cụ đang được chọn (UC2 requirement)
// ============================================================
function initCustomCursor() {
    const toolButtons = document.querySelectorAll('.tool-btn');
    const body = document.body;

    // Tạo con trỏ tùy chỉnh dạng emoji floating
    const cursorDiv = document.createElement('div');
    cursorDiv.id = 'custom-cursor';
    cursorDiv.style.cssText = `
        position: fixed;
        width: 40px;
        height: 40px;
        font-size: 28px;
        pointer-events: none;
        z-index: 9999;
        transform: translate(-50%, -50%);
        transition: transform 0.1s ease;
        display: none;
    `;
    document.body.appendChild(cursorDiv);

    // Theo dõi vị trí chuột để di chuyển cursor
    document.addEventListener('mousemove', (e) => {
        cursorDiv.style.left = e.clientX + 'px';
        cursorDiv.style.top = e.clientY + 'px';
    });

    // Map từ toolId sang emoji cursor (UC2 – nhận diện công cụ trực quan)
    // comb=Lược, dryer=Máy sấy, feather=Lông vũ, spray=Bình xịt
    const toolCursorMap = {
        'comb':    '🪮',  // [UC4] Lược chải lông → CARE
        'dryer':   '💨',  // [UC4] Máy sấy → CARE
        'feather': '🪶',  // [UC3] Lông vũ → PRANK
        'spray':   '💦'   // [UC3] Bình xịt → PRANK
    };

    toolButtons.forEach(btn => {
        const form = btn.closest('form');
        const toolInput = form ? form.querySelector('input[name="toolId"]') : null;
        const toolId = toolInput ? toolInput.value : null;
        const cursorEmoji = toolCursorMap[toolId] || '✋';

        // Hiển thị emoji cursor khi hover vào nút dụng cụ
        btn.addEventListener('mouseenter', () => {
            cursorDiv.style.display = 'block';
            cursorDiv.textContent = cursorEmoji;
            body.style.cursor = 'none';
            cursorDiv.style.transform = 'translate(-50%, -50%) scale(1.2)';
        });

        // Ẩn emoji cursor khi rời khỏi nút
        btn.addEventListener('mouseleave', () => {
            cursorDiv.style.display = 'none';
            body.style.cursor = '';
            cursorDiv.style.transform = 'translate(-50%, -50%) scale(1)';
        });

        // Animation nhấn xuống khi click
        btn.addEventListener('click', () => {
            cursorDiv.style.transform = 'translate(-50%, -50%) scale(0.8) rotate(20deg)';
            setTimeout(() => {
                cursorDiv.style.transform = 'translate(-50%, -50%) scale(1)';
            }, 200);
        });
    });
}

/**
 * [UC4 – CHĂM SÓC] HIỆU ỨNG CONFETTI KHI CARE (Hạnh phúc tăng)
 *    12 hạt màu bay ra từ trung tâm màn hình khi lastReaction="happy"
 */
function spawnConfetti() {
    const colors = ['#c77dff', '#ff6b9d', '#ffd166', '#06d6a0', '#4cc9f0'];
    for (let i = 0; i < 12; i++) {
        const dot = document.createElement('div');
        dot.style.cssText = `
            position: fixed;
            width: ${4 + Math.random() * 6}px;
            height: ${4 + Math.random() * 6}px;
            background: ${colors[Math.floor(Math.random() * colors.length)]};
            border-radius: 50%;
            pointer-events: none;
            z-index: 1000;
            left: ${30 + Math.random() * 40}%;
            top: 50%;
        `;
        document.body.appendChild(dot);

        const angle = -90 + (Math.random() - 0.5) * 180;
        const distance = 80 + Math.random() * 120;
        const rad = (angle * Math.PI) / 180;
        const dx = Math.cos(rad) * distance;
        const dy = Math.sin(rad) * distance;

        dot.animate([
            { transform: 'translate(0,0) scale(1)', opacity: 1 },
            { transform: `translate(${dx}px, ${dy}px) scale(0)`, opacity: 0 }
        ], {
            duration: 600 + Math.random() * 400,
            easing: 'cubic-bezier(0,0.9,0.57,1)',
            fill: 'forwards'
        }).onfinish = () => dot.remove();
    }
}

/**
 * [UC4 kết thúc – VICTORY] HIỆU ỨNG CONFETTI LIÊN TỤC
 *    Phát mưa confetti liên tục khi tải trang victory.html
 *    Mạnh hơn spawnConfetti() – nhiều hạt hơn, vòng lặp liên tục
 */
function spawnVictoryConfetti() {
    const colors = ['#ffd166', '#06d6a0', '#c77dff', '#ff6b9d', '#4cc9f0', '#ffffff'];
    const shapes = ['50%', '0%']; // circle, square
    for (let i = 0; i < 30; i++) {
        const dot = document.createElement('div');
        const size = 6 + Math.random() * 10;
        dot.style.cssText = `
            position: fixed;
            width: ${size}px;
            height: ${size}px;
            background: ${colors[Math.floor(Math.random() * colors.length)]};
            border-radius: ${shapes[Math.floor(Math.random() * shapes.length)]};
            pointer-events: none;
            z-index: 9999;
            left: ${Math.random() * 100}%;
            top: -20px;
        `;
        document.body.appendChild(dot);

        const fallDuration = 1800 + Math.random() * 2000;
        const delay = Math.random() * 800;
        const targetX = (Math.random() - 0.5) * 200;

        dot.animate([
            { transform: `translateY(0) translateX(0) rotate(0deg)`, opacity: 1 },
            { transform: `translateY(110vh) translateX(${targetX}px) rotate(${360 + Math.random()*720}deg)`, opacity: 0.3 }
        ], {
            duration: fallDuration,
            delay: delay,
            easing: 'ease-in',
            fill: 'forwards'
        }).onfinish = () => dot.remove();
    }
}

// ============================================================
// 5. [UC3 kết thúc – PENALTY] HIỆU ỨNG RUNG MÀN HÌNH
//    Phát khi tải trang penalty.html – tăng cảm giác "bị phạt"
// ============================================================
function shakeScreen() {
    document.body.animate([
        { transform: 'translateX(0)' },
        { transform: 'translateX(-8px) rotate(-1deg)' },
        { transform: 'translateX(8px) rotate(1deg)' },
        { transform: 'translateX(-6px)' },
        { transform: 'translateX(6px)' },
        { transform: 'translateX(0)' }
    ], { duration: 500, easing: 'ease-in-out' });
}

// ============================================================
// KHỞI ĐỘNG KHI TRANG LOAD XONG
// ============================================================
document.addEventListener('DOMContentLoaded', () => {
    // [UC2] Khởi tạo custom cursor cho khay dụng cụ
    initCustomCursor();

    // [UC3+UC4] Phát âm thanh tương ứng với phản ứng thú cưng
    initGameAudio();

    // [UC4] Nếu lastReaction là "happy" → bắn confetti
    if (typeof lastReaction !== 'undefined' && lastReaction === 'happy') {
        setTimeout(spawnConfetti, 100);
    }

    // [UC3 Penalty] Nếu đang ở trang penalty → rung màn hình + âm thanh crash
    if (document.querySelector('.penalty-container')) {
        shakeScreen();
        playPenaltySound();
    }

    // [UC4 Victory] Nếu đang ở trang victory → mưa confetti + âm thanh fanfare
    if (document.querySelector('.victory-container')) {
        playVictorySound();
        // Bắn confetti nhiều đợt
        spawnVictoryConfetti();
        setTimeout(spawnVictoryConfetti, 1000);
        setTimeout(spawnVictoryConfetti, 2000);
    }
});
