/**
 * MY SILLY BESTIE – script.js
 * Xử lý:
 *  1. Web Audio API – tạo âm thanh không cần file .mp3
 *  2. Custom Cursor – con trỏ đổi hình khi hover dụng cụ
 *  3. Hiệu ứng hạt confetti nhỏ khi tương tác CARE
 */

// ============================================================
// 1. WEB AUDIO API – Tổng hợp âm thanh trực tiếp từ trình duyệt
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
 * Tạo âm thanh dịu dàng kiểu "ding" cho CARE
 * Giống tiếng nhạc hộp nhỏ
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
 * Tạo âm thanh tinh nghịch kiểu "boing" cho PRANK
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
 * Tạo âm thanh phạt kiểu "crash" cho Penalty
 */
function playPenaltySound() {
    try {
        const ctx = getAudioCtx();
        // Tiếng trống rung
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

        // Thêm tiếng "wah"
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

// ============================================================
// 2. TỰ ĐỘNG PHÁT ÂM THANH DỰA TRÊN TRẠNG THÁI GAME
// ============================================================
function initGameAudio() {
    // lastReaction được inject bởi Thymeleaf inline JS trong game.html
    if (typeof lastReaction === 'undefined') return;

    if (lastReaction === 'happy') {
        playCareSound();
    } else if (lastReaction === 'suspicious') {
        playPrankSound();
    } else if (lastReaction === 'angry') {
        playPenaltySound();
    }
}

// ============================================================
// 3. CUSTOM CURSOR – Đổi con trỏ thành icon dụng cụ khi hover
// ============================================================
function initCustomCursor() {
    const toolButtons = document.querySelectorAll('.tool-btn');
    const body = document.body;

    // Tạo con trỏ tùy chỉnh dạng emoji
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

    // Theo dõi chuột
    document.addEventListener('mousemove', (e) => {
        cursorDiv.style.left = e.clientX + 'px';
        cursorDiv.style.top = e.clientY + 'px';
    });

    // Map từ toolId sang emoji cursor
    const toolCursorMap = {
        'comb':    '🪮',
        'dryer':   '💨',
        'feather': '🪶',
        'spray':   '💦'
    };

    toolButtons.forEach(btn => {
        const form = btn.closest('form');
        const toolInput = form ? form.querySelector('input[name="toolId"]') : null;
        const toolId = toolInput ? toolInput.value : null;
        const cursorEmoji = toolCursorMap[toolId] || '✋';

        btn.addEventListener('mouseenter', () => {
            cursorDiv.style.display = 'block';
            cursorDiv.textContent = cursorEmoji;
            body.style.cursor = 'none';
            cursorDiv.style.transform = 'translate(-50%, -50%) scale(1.2)';
        });

        btn.addEventListener('mouseleave', () => {
            cursorDiv.style.display = 'none';
            body.style.cursor = '';
            cursorDiv.style.transform = 'translate(-50%, -50%) scale(1)';
        });

        btn.addEventListener('click', () => {
            cursorDiv.style.transform = 'translate(-50%, -50%) scale(0.8) rotate(20deg)';
            setTimeout(() => {
                cursorDiv.style.transform = 'translate(-50%, -50%) scale(1)';
            }, 200);
        });
    });
}

// ============================================================
// 4. HIỆU ỨNG CONFETTI KHI CARE (Hạnh phúc tăng)
// ============================================================
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

// ============================================================
// 5. HIỆU ỨNG RUNG MÀN HÌNH KHI PENALTY
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
    initCustomCursor();
    initGameAudio();

    // Nếu lastReaction là happy → confetti
    if (typeof lastReaction !== 'undefined' && lastReaction === 'happy') {
        setTimeout(spawnConfetti, 100);
    }

    // Nếu đang ở trang penalty → rung màn hình
    if (document.querySelector('.penalty-container')) {
        shakeScreen();
        playPenaltySound();
    }
});
