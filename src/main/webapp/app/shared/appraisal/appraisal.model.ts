export class Appraisal {
    constructor(
        public link?: string,
        public additionalRaw?: string,
        public raw?: string,
        public buybackPrice?: number,
        public items?: any[],
        public jitaBuy?: number,
        public jitaSell?: number,
    ) {
    }
}
