const solution = (n, t, m, p) => {
    let answer = "";
    let num = 0;
    let count = 1;
    let exit = false;

    while (!exit) {
        let converted = convert(num++, n);

        for (c of converted) {
            if (t == 0) {
                exit = true;
                break;
            }

            if (count % m == p || (count % m == 0 && m == p)) {
                answer += c;
                t--;
            }

            count++;
        }
    }

    return answer;
};

const convert = (num, radix) => {
    if (num == 0) return "0";

    let converted = "";

    while (num > 0) {
        let quotient = num / radix;
        let remainder = num % radix;

        switch (remainder) {
            case 10:
                converted = "A" + converted;
                break;
            case 11:
                converted = "B" + converted;
                break;
            case 12:
                converted = "C" + converted;
                break;
            case 13:
                converted = "D" + converted;
                break;
            case 14:
                converted = "E" + converted;
                break;
            case 15:
                converted = "F" + converted;
                break;
            default:
                converted = remainder + converted;
        }

        num = parseInt(quotient);
    }

    return converted;
};

solution(2, 4, 2, 1);
// solution(16, 16, 2, 1);
// solution(16, 16, 2, 2);
