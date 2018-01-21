import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'statusNamePipe'})
export class StatusNamePipe implements PipeTransform {

    transform(value: any, args?: any): string {
        if (value === 'MISSING') {
            return 'Missing'
        } else if (value === 'OVER_PRICED') {
            return 'Over priced'
        } else if (value === 'WELL_PRICED') {
            return 'Stocked'
        } else {
            return value;
        }
    }

}
