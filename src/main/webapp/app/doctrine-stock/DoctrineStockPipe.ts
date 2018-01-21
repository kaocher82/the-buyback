import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'doctrineStockPipe'})
export class DoctrineStockPipe implements PipeTransform {

    transform(value: any[], showStocked: boolean, showMissingOnly: boolean): any[] {
        const result = [];
        for (let i = 0; i < value.length; i++) {
            if (this.showEntry(showStocked, showMissingOnly, value[i]['availability'])) {
                result.push(value[i]);
            }
        }
        return result;
    }

    showEntry(showStocked: boolean, showMissingOnly: boolean, availability: string) {
        if (showMissingOnly && availability === 'MISSING') {
            return true;
        }
        if (showMissingOnly && availability !== 'MISSING') {
            return false;
        }
        if (showStocked && availability === 'WELL_PRICED') {
            return true;
        }
        if (!showStocked && availability === 'WELL_PRICED') {
            return false;
        }
        return true;
    }

}
